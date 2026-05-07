package entity

/**
 * Represents a card in a deck of cards
 *
 * @constructor Creates a card with its suit and value.
 * @property suit The [CardSuit] of the card.
 * @property value The [CardValue] of the card.
 * @property faceDown True if the card is face down.
 */
class Card(val suit: CardSuit, val value: CardValue, var faceDown: Boolean = true) {

    /** Flips the card face up. */
    fun flip() {
        faceDown = false
    }

    override fun toString() = "${value.title} of ${suit.suitName}"

    /** Returns if two cards are the same. */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false

        return (value == other.value && suit == other.suit)
    }

    /** Returns the hashCode for a card. */
    override fun hashCode(): Int {
        var result = suit.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}
