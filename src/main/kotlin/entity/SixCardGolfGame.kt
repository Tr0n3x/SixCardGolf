package entity

/**
 * Represents a SixCardGolfGame game.
 *
 * @constructor Creates a SixCardGolfGame game with given players.
 * @param players The players of the game.
 * @property players The players of the game.
 */
class SixCardGolfGame(val players: MutableList<Player>) {
    var finalPlays: Int? = null
    var currentPlayer: Int = 0
    var drawStack: MutableList<Card> = mutableListOf()
    var discardStack: MutableList<Card> = mutableListOf()
    var currentAction: Action? = null
    var hand: Card? = null
}