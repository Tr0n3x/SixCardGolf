package entity

/**
 * Represents a player in a SixCardGolfGame game.
 *
 * @constructor Creates a player with the given name.
 * @param name The name of the player.
 * @property name The name of the player.
 */
class Player(val name: String) {
    var score: Int = 0
    var lastAction: String = ""
    val slots: MutableList<Card?> = MutableList(6) { null }
}
