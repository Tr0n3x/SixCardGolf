package entity

import java.util.*

/**
 * Enum to distinguish between the 13 possible values in a french-suited card game:
 * 2-10, Jack, Queen, King, and Ace.
 *
 * While some values are different:
 * TWO equals -2, JACK,QUEEN,KING equal 10 and ACE equals 1.
 *
 * @property title The name of the card.
 * @property value The value of the card.
 */
enum class CardValue(val title: String, val value: Int) {
    TWO("Two", -2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 10),
    QUEEN("Queen", 10),
    KING("King", 10),
    ACE("Ace", 1),
    ;

    /**
     * Provides utility functions for working with [CardValue].
     */
    companion object {
        /**
         * A set of values for a reduced set of 3*4*4=48 cards (+4 to get 52 cards)
         */
        fun shortDeck(): Set<CardValue> {
            return EnumSet.of(TWO, KING, QUEEN)
        }
    }
}
