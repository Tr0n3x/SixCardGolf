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

    /** Gives the card as a string. */
    override fun toString() = "${value.title} of ${suit.suitName}"
}
