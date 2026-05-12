package entity

/**
 *  Represents a SixCardGolfGame game.
 *
 *  @constructor Creates a SixCardGolfGame game with given players.
 *  @property players The players of the game.
 *  @property finalPlays If the last plays are to be made.
 *  @property currentPlayer The index of current player
 *  @property deck The deck.
 *  @property discard The discard pile.
 *  @property currentAction The current action the player performs.
 *  @property hand The card in the hand slot.
 */
class SixCardGolfGame(
    val players: MutableList<Player>,
    var finalPlays: Int? = null,
    var currentPlayer: Int = 0,
    var deck: MutableList<Card> = mutableListOf(),
    var discard: MutableList<Card> = mutableListOf(),
    var currentAction: Action? = null,
    var hand: Card? = null,
    var finalRound: Boolean = false,
)
