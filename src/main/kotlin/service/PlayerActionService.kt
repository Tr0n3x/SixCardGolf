package service

import entity.Player

/**
 * This class is responsible for the player actions of the game.
 *
 * @constructor Creates a new player action instance
 * @property rootService The [RootService] manages the game state.
 */
class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {
    /** The current game instance. */
    private val game get() = requireNotNull(rootService.currentGame) { "Game not found" }

    /** Initiates the last round if all requirements are met. */
    private fun finishMove() {
        if (allCardsOpen(game.players[game.currentPlayer])) {
            if (!game.finalRound) {
                game.finalRound = true
                game.finalPlays = game.players.size - 1
            } else {
                game.finalPlays = game.finalPlays?.minus(game.finalPlays!!)
            }
        }
        if (game.finalPlays == 0) {
            endGame()
        } else {
            rootService.gameService.nextPlayer()
        }
    }

    /**
     * Ends the game.
     *
     * Flips all cards that have not been flipped yet, removes identical rows,
     * calculates and shows scoreboard
     */
    private fun endGame() {
        for (player in game.players) {
            for (card in player.slots) {
                if (card != null && card.faceDown) {
                    card.flip()
                }
            }
            removeRow(player,0)
            removeRow(player,1)
            calculateScore(player)
        }
    }

    /**
     * Removes a row if all cards in row are not null,
     * are faceUp and the same value title
     *
     * @param player The player whose row should be checked.
     * @param row The row to be checked (0 for slots 0,2,4 or 1 for slots 1,3,5)
     * @throws IllegalArgumentException If [row] is not 0 or 1.
     */
    private fun removeRow(player: Player, row: Int) {
        val card0 = player.slots[0]
        val card1 = player.slots[1]
        val card2 = player.slots[2]
        val card3 = player.slots[3]
        val card4 = player.slots[4]
        val card5 = player.slots[5]

        if (row != 0 && row != 1) {
            throw IllegalArgumentException("Row has to be 0 or 1.")
        }
        if (row == 0
            && card0 != null && card2 != null && card4 != null
            && !card0.faceDown && !card2.faceDown && !card4.faceDown
            && card0.value.title == card2.value.title && card0.value.title == card4.value.title
        ) {
            player.slots[0] = null
            player.slots[2] = null
            player.slots[4] = null
        }
        if (row == 1
            && card1 != null && card3 != null && card5 != null
            && !card1.faceDown && !card3.faceDown && !card5.faceDown
            && card1.value.title == card3.value.title && card1.value.title == card5.value.title
        ) {
            player.slots[1] = null
            player.slots[3] = null
            player.slots[5] = null
        }
    }

    /**
     * Checks whether two specific slots have the same value title.
     *
     * @param player The player whose slots should be checked.
     * @param cardSlot1 The first slot to be checked.
     * @param cardSlot2 The second slot to be checked.
     * @return true if [cardSlot1] and [cardSlot2] hold the same titled card, otherwise false.
     */
    private fun boolSlotsEqual(player: Player, cardSlot1: Int, cardSlot2: Int): Boolean {
        return player.slots[cardSlot1]?.value?.title == player.slots[cardSlot2]?.value?.title
    }

    /** Calculates the score of the given player.
     *
     * @param player The player whose score will be calculated.
     * @return The score of the player.
     */
    private fun calculateScore(player: Player): Int {
        var i = 0
        while (i < player.slots.size) {
            if (!boolSlotsEqual(player, i, i + 1)) {
                player.score += player.slots[i]?.value?.value ?: 0
                player.score += player.slots[i + 1]?.value?.value ?: 0
            }
            i += 2
        }
        return player.score
    }

    /** Returns if all cards are face up */
    private fun allCardsOpen(player: Player): Boolean {
        return player.slots.all { it?.faceDown == false }
    }

    //fun flipCard(slot?: Int) {}
    fun drawCard(discard: Boolean) {}
    //fun placeCard(slot?: Int) {}
}
