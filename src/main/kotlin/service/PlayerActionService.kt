package service

import entity.Action
import entity.Card
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

    private var flippedThisTurn = 0

    /** Initiates the last round if all requirements are met. */
    private fun finishMove() {
        if (allCardsOpen(game.players[game.currentPlayer])) {
            if (!game.finalRound) {
                game.finalRound = true
                game.finalPlays = game.players.size - 1
            } else {
                game.finalPlays = game.finalPlays?.minus(1)
            }
        }
        if (game.finalPlays == 0) {
            endGame()
        } else {
            game.players[game.currentPlayer].lastAction = ""
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
        onAllRefreshables { refreshAfterGameEnd() }
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
        val cardsRow0 = listOf(player.slots[0], player.slots[2], player.slots[4])
        val cardsRow1 = listOf(player.slots[1], player.slots[3], player.slots[5])

        val cards = if (row == 0) cardsRow0 else cardsRow1
        val indices = if (row == 0) intArrayOf(0, 2, 4) else intArrayOf(1, 3, 5)

        if (row != 0 && row != 1) {
            throw IllegalArgumentException("Row has to be 0 or 1.")
        }
        if (allCardsNotNull(cards) && allCardsFlipped(cards) && sameValueCards(cards)) {
            indices.forEach { player.slots[it] = null }
            onAllRefreshables { refreshAfterRowRemove(indices) }
        }
    }

    /**
     * Checks whether a list of cards has the same value title.
     *
     * @return true if all cards have the same value title, otherwise false.
     */
    private fun sameValueCards(cards: List<Card?>): Boolean =
        cards.filterNotNull().map { it.value.title }.distinct().size == 1

    /**
     * Checks whether a list of cards is flipped.
     *
     * @return true if all cards are flipped, otherwise false.
     */
    private fun allCardsFlipped(cards: List<Card?>): Boolean =
        cards.filterNotNull().all { !it.faceDown }

    /**
     * Checks whether a list of cards is not null.
     *
     * @return true if all cards are not null, otherwise false.
     */
    private fun allCardsNotNull(cards: List<Card?>): Boolean =
        cards.all { it != null }

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

    /** Returns if all cards are face up. */
    private fun allCardsOpen(player: Player): Boolean {
        return player.slots.all { it?.faceDown == false }
    }

    /**
     * Flips a card from the card slots
     *
     * @param slot The slot in which the card will be flipped
     * @throws IllegalStateException If this action is not allowed at this time.
     * @throws IllegalStateException If the player tried to skip on first flip.
     */
    fun flipCard(slot: Int?) {
        if (game.currentAction == Action.DRAW_DECK || game.currentAction == Action.DRAW_DISCARD) {
            throw IllegalStateException("This move is not allowed.")
        }
        val player = game.players[game.currentPlayer]
        if (flippedThisTurn == 0 && slot == null) {
            throw IllegalStateException("You are not allowed to skip on first flip.")
        }
        if(slot != null) {
            player.slots[slot]?.flip()
            onAllRefreshables { refreshAfterFlipCard() }
            val card = player.slots[slot]
            player.lastAction += "Flipped $card in slot $slot. "
        }
        game.currentAction = Action.FLIP
        flippedThisTurn++
        removeRow(player,0)
        removeRow(player,1)
        if (flippedThisTurn >= 2) {
            finishMove()
            flippedThisTurn = 0
        }
    }

    /**
     * Draws a card from the deck or the discard pile and places it in game.hand.
     *
     * @param discard true if drawn from discard, false if drawn from deck.
     * @throws IllegalStateException If a certain action is not allowed.
     */
    fun drawCard(discard: Boolean) {
        if (game.currentAction != null) {
            throw IllegalStateException("This move is not allowed.")
        }
        val player = game.players[game.currentPlayer]
        if(discard) {
            if(game.discard.isNotEmpty()) {
                val card = game.discard.removeLast()
                onAllRefreshables { refreshAfterDiscardTake() }
                game.hand = card
                game.hand?.flip()
                onAllRefreshables { refreshAfterHandPlace() }
                game.currentAction = Action.DRAW_DISCARD
                player.lastAction += "Drew ${game.hand} from discard. "
            }
        } else {
            if(game.deck.isNotEmpty()) {
                val card = game.deck.removeLast()
                onAllRefreshables { refreshAfterDeckTake() }
                game.hand = card
                game.hand?.flip()
                onAllRefreshables { refreshAfterHandPlace() }
                game.currentAction = Action.DRAW_DECK
                player.lastAction += "Drew ${game.hand} from deck. "
            }
        }
    }

    /**
     * Places the drawn card from game.hand.
     *
     * If [slot] is null, the card in game.hand is discarded.
     * Otherwise, the card in the given slot (0–5) is replaced with the card from game.hand.
     * The previous card in the slot is turned face up if necessary and moved to the discard pile.
     *
     * @param slot null to discard the drawn card, or the slot index (0–5) to replace.
     * @throws IllegalStateException If a certain action is not allowed.
     */
    fun placeCard(slot: Int?) {
        if (game.currentAction != Action.DRAW_DECK &&
            game.currentAction != Action.DRAW_DISCARD) {
            throw IllegalStateException("This move is not allowed.")
        }
        val player = game.players[game.currentPlayer]
        if (slot == null) {
            if (game.currentAction != Action.DRAW_DECK) {
                throw IllegalStateException("This move is not allowed.")
            }
            val card = game.hand
            game.hand?.let { game.discard.add(it) }
            onAllRefreshables { refreshAfterDiscardPlace() }
            game.hand = null
            onAllRefreshables { refreshAfterHandPlace() }
            player.lastAction += "Discarded drawn card $card. "
            game.currentAction = Action.FLIP
            flippedThisTurn = 1
            return
        }
        val oldCard = player.slots[slot]
        player.slots[slot]?.let {
            if (it.faceDown) it.flip()
            game.discard.add(it)
            onAllRefreshables { refreshAfterDiscardPlace() }
        }
        val newCard = game.hand
        game.hand?.let {
            player.slots[slot] = it
            onAllRefreshables { refreshAfterPlaceCard() }
        }
        game.hand = null
        onAllRefreshables { refreshAfterHandPlace() }
        player.lastAction += "Replaced $oldCard with $newCard in slot $slot. "
        removeRow(player, 0)
        removeRow(player, 1)
        finishMove()
    }
}
