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

    /**
     * Initiates the last round if all requirements are met.
     *
     * If [allCardsOpen] is true, the `finalRound` starts.
     * Otherwise, the `lastAction` will be reset and the next players turn starts.
     */
    private fun finishMove() {
        val player = game.players[game.currentPlayer]
        if (allCardsOpen(player) && !game.finalRound) {
            game.finalRound = true
            game.finalPlays = game.players.size
        }

        if (game.finalRound) {
            game.finalPlays = game.finalPlays?.minus(1)
        }

        if (game.finalPlays == 0) {
            onAllRefreshables { refreshAfterEndTurn() }
            endGame()
        } else {
            rootService.gameService.nextPlayer()
            game.players[game.currentPlayer].lastAction = ""
        }
    }

    /**
     * Ends the game.
     *
     * Flips all cards that have not been flipped yet, removes identical rows via [removeRow],
     * calculates and shows scoreboard via [calculateScore].
     */
    private fun endGame() {
        for (i in game.players.indices) {
            game.currentPlayer = i
            val player = game.players[i]
            for (card in player.slots) {
                if (card != null && card.faceDown) {
                    card.flip()
                }
            }

            removeRow(player, 0)
            removeRow(player, 1)
        }

        calculateScore()
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

        if (allCardsNotNull(cards) && allCardsFlipped(cards) && sameValueCards(cards)) {
            indices.forEach { player.slots[it] = null }
            onAllRefreshables { refreshAfterRowRemove(indices) }
        }

    }

    /**
     * Checks whether a list of cards has the same value title.
     *
     * @param cards List of cards which shall be checked for value title.
     * @return true if all cards have the same value title, otherwise false.
     */
    private fun sameValueCards(cards: List<Card?>): Boolean =
        cards.filterNotNull().map { it.value.title }.distinct().size == 1

    /**
     * Checks whether a list of cards is flipped.
     *
     * @param cards List of cards which shall be checked if they are `faceDown`.
     * @return true if all cards are flipped, otherwise false.
     */
    private fun allCardsFlipped(cards: List<Card?>): Boolean =
        cards.filterNotNull().all { !it.faceDown }

    /**
     * Checks whether a list of cards is not null.
     *
     * @param cards List of cards which shall be checked if cards are present.
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

    /** Calculates the score of all player. */
    private fun calculateScore() {
        for (player in game.players) {
            var cardSlot = 0
            while (cardSlot < player.slots.size) {
                if (!boolSlotsEqual(player, cardSlot, cardSlot + 1)) {
                    player.score = player.score?.plus(player.slots[cardSlot]?.value?.value ?: 0)
                    player.score = player.score?.plus(player.slots[cardSlot + 1]?.value?.value ?: 0)
                }
                cardSlot += 2
            }
        }
    }

    /**
     * Returns if all cards are face up.
     *
     * @param player The player for whom will be checked if all cards are indeed open
     * @return true if all cards are open or null. Otherwise, false.
     */
    fun allCardsOpen(player: Player): Boolean {
        return player.slots.all { it == null || !it.faceDown }
    }

    /**
     * Entry Point to flip a card from the card slots.
     *
     * Uses [initialFlipCard] and [regularFlipCard] for the game logic.
     *
     * @param slot The slot in which the card will be flipped
     * @throws IllegalArgumentException If the slot is neither null nor a number between 0 and 5.
     */
    fun flipCard(slot: Int?) {
        isNoGameRunning()

        if (slot != null && slot !in 0..5) {
            throw IllegalArgumentException("Slot has to be between 0 and 5 or null!")
        }

        if (game.round == 0) {
            initialFlipCard(slot)
        } else {
            regularFlipCard(slot)
        }
    }

    /**
     * The flip logic for the initial round (round 0).
     *
     * @param slot The slot in which the card will be flipped.
     * @throws IllegalArgumentException When the player tries to skip flipping.
     */
    private fun initialFlipCard(slot: Int?) {
        if (slot == null) {
            throw IllegalArgumentException("You must flip a card.")
        }

        val player = game.players[game.currentPlayer]
        val card = player.slots[slot]

        card?.flip()
        player.lastAction += "Flipped $card. "
        onAllRefreshables { refreshAfterFlipCard(slot) }

        flippedThisTurn++

        if (flippedThisTurn == 2) {
            flippedThisTurn = 0

            if (game.currentPlayer == game.players.size - 1) {
                game.round = 1
                game.players[0].lastAction = ""
            }
            rootService.gameService.nextPlayer()
        }
    }

    /**
     * The flip logic for the regular game round (round 1+).
     *
     * @param slot The slot in which the card will be flipped.
     * @throws IllegalArgumentException When the player tries to skip flipping.
     */
    private fun regularFlipCard(slot: Int?) {
        val player = game.players[game.currentPlayer]

        if (game.currentAction == Action.DRAW_DECK || game.currentAction == Action.DRAW_DISCARD) {
            throw IllegalStateException("This move is not allowed.")
        }

        if (flippedThisTurn == 0 && slot == null) {
            throw IllegalStateException("You are not allowed to skip on first flip.")
        }

        if (slot != null) {
            val card = player.slots[slot]
            card?.flip()

            onAllRefreshables { refreshAfterFlipCard(slot) }

            player.lastAction += "Flipped $card. "

            game.currentAction = Action.FLIP
            flippedThisTurn++

            removeRow(player, 0)
            removeRow(player, 1)

            if (flippedThisTurn >= 2 || allCardsOpen(player)) {
                finishMove()
                flippedThisTurn = 0
            }
        } else {
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
        isNoGameRunning()

        if (game.currentAction != null || game.round == 0) {
            throw IllegalStateException("This move is not allowed.")
        }

        // draw from discard
        if (discard) {
            if (game.discard.isNotEmpty()) {
                val card = game.discard.removeLast()
                onAllRefreshables { refreshAfterDiscardTake() }
                game.hand = card
                onAllRefreshables { refreshAfterHandChange() }
                game.currentAction = Action.DRAW_DISCARD
            }
            // draw from deck
        } else {
            if (game.deck.isNotEmpty()) {
                val card = game.deck.removeLast()
                onAllRefreshables { refreshAfterDeckTake() }
                game.hand = card
                game.hand?.flip()
                onAllRefreshables { refreshAfterHandChange() }
                game.currentAction = Action.DRAW_DECK
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
        isNoGameRunning()

        if ((game.currentAction != Action.DRAW_DECK && game.currentAction != Action.DRAW_DISCARD) || game.round == 0) {
            throw IllegalStateException("This move is not allowed.")
        }

        val player = game.players[game.currentPlayer]

        // player drew from deck, so flipCard is allowed afterward. Therefore, currentAction has to be Action.FLIP
        if (slot == null) {
            if (game.currentAction != Action.DRAW_DECK) {
                throw IllegalStateException("This move is not allowed.")
            }
            val card = game.hand
            game.hand?.let { game.discard.add(it) }
            onAllRefreshables { refreshAfterDiscardPlace() }
            game.hand = null
            onAllRefreshables { refreshAfterHandChange() }
            player.lastAction = "Discarded drawn card $card. "
            game.currentAction = Action.FLIP
            flippedThisTurn = 1

            return
        }

        // Replacing the card in a slot with the card from the hand
        val oldCard = player.slots[slot]

        player.slots[slot]?.let {
            if (it.faceDown) it.flip()
            game.discard.add(it)
            onAllRefreshables { refreshAfterDiscardPlace() }
        }

        val newCard = game.hand

        game.hand?.let {
            player.slots[slot] = it
            onAllRefreshables { refreshAfterPlaceCard(slot) }
        }

        game.hand = null
        onAllRefreshables { refreshAfterHandChange() }
        player.lastAction = "Replaced $oldCard with drawn card $newCard. "

        removeRow(player, 0)
        removeRow(player, 1)
        finishMove()
    }

    /**
     * Returns with an error if no game is running.
     *
     * @throws IllegalStateException If no game is currently running.
     */
    private fun isNoGameRunning() {
        if (rootService.currentGame == null) {
            throw IllegalStateException("Game not found.")
        }
    }
}
