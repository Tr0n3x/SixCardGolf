package entity

/**
 * Represents a player in a entity.SixCardGolfGame game.
 *
 * @constructor Creates a player with the given name.
 * @property name The name of the player.
 */
class Player(val name: String) {
    var score: Int = 0
    var lastAction: String = ""
    val slots: MutableList<Card?> = MutableList(6) { null }
}
