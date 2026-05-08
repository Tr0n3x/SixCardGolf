package entity

/**
 * Represents a player in an entity.SixCardGolfGame game.
 *
 * @constructor Creates a player with the given name.
 * @property name The name of the player.
 * @property score The score of the player.
 * @property lastAction The log of the last action the player made.
 * @property slots The card slots of the player.
 */
class Player(
    val name: String,
    var score: Int = 0,
    var lastAction: String = "",
    val slots: MutableList<Card?> = MutableList(6) { null }
)
