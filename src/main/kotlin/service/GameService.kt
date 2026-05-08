package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import entity.SixCardGolfGame

/**
 * This class is responsible for the modifications of the game. It enforces the game rules.
 *
 * @constructor Creates a new game instance
 * @property rootService The [RootService] manages the game state.
 */
class GameService(private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * This method starts a new Six Card Golf game.
     * Creates the players with given names, initializes and shuffles the deck,
     * distributes cards to each player, places first card flipped on the discard pile.
     * Optionally starts the game in testMode or randomizes the play order.
     *
     * @param playerNames The names of the participating player
     * @param randomOrder If true the play order will be randomized
     * @param testMode If true the deck, created by [createShortDeck] will be used.
     * @throws IllegalStateException If the number of players is not between 2 and 4.
     */
    fun startGame(playerNames: List<String>, randomOrder: Boolean, testMode: Boolean ) {
        val playerList = playerNames.map { Player(it) }
        val game = SixCardGolfGame(playerList.toMutableList())
        if(playerList.size !in 2..4) {
            throw IllegalStateException("2 to 4 players are required to start the game")
        }
        if(testMode) {
            val deck = createShortDeck()
            game.deck = deck
        } else {
            val deck = createDeck()
            game.deck = deck
        }
        game.deck.shuffle()
        for(player in game.players) {
            for (i in player.slots.indices) {
                player.slots[i] = game.deck.removeLast() // Potential removeFirst()
            }
        }
        game.discard.add(game.deck.removeLast())
        game.discard.last().flip()
        if(randomOrder) {
            game.players.shuffle()
        }
        game.currentPlayer = 0

        rootService.currentGame = game
    }

    /** Starts the turn of the next Player */
    fun nextPlayer() {
        val game = rootService.currentGame
            ?: throw IllegalStateException("Game not started")
        game.currentPlayer = (game.currentPlayer + 1) % game.players.size
    }

    /**
     * Creates a full deck of cards containing all combinations of [CardSuit] and [CardValue].
     * @return A list representing the complete deck of cards.
     */
    fun createDeck(): MutableList<Card> {
        val deck = mutableListOf<Card>()
        for (suit in CardSuit.entries) {
            for (value in CardValue.entries) {
                deck.add(Card(suit, value, true))
            }
        }
        return deck
    }
    /**
     * TODO Has to be reworked for correct functionality!
     * Creates a short deck of cards containing all combinations of [CardSuit] and [CardValue].
     * @return A list representing the short deck of cards.
     */
    fun createShortDeck(): MutableList<Card> {
        val deck = mutableListOf<Card>()
        for (suit in CardSuit.entries) {
            for (value in CardValue.shortDeck())
                deck.add(Card(suit, value, true))
        }
        return deck
    }
}