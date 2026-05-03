package entity

/**
 * Represents a card in a deck of cards
 *
 * @constructor Creates a card with its suit and value.
 * @property suit The [CardSuit] of the card.
 * @property value The [CardValue] of the card.
 */
class Card(val suit: CardSuit, val value: CardValue, var faceDown: Boolean = true) {

    fun flip() {
        faceDown = false
    }

    override fun toString() = "$value of $suit"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false

        return value == other.value
    }

    override fun hashCode(): Int {
        var result = suit.cardName.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}