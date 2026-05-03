package entity

/**
 * Represents a SixCardGolfGame game.
 *
 * @constructor Creates a SixCardGolfGame game with given players.
 * @property players The players of the game.
 */
class SixCardGolfGame(val players: MutableList<Player>) {
    var finalPlays: Int? = null
    var currentPlayer: Int = 0
    var deck: MutableList<Card> = mutableListOf()
    var discard: MutableList<Card> = mutableListOf()
    var currentAction: Action? = null
    var hand: Card? = null
    var final: Boolean = false
}