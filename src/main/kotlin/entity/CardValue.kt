package entity

import java.util.*

/**
 * Enum to distinguish between the 13 possible values in a french-suited card game:
 * 2-10, Jack, Queen, King, and Ace.
 *
 * While some values are different:
 * TWO equals -2, JACK,QUEEN,KING equal 10 and ACE equals 1.
 *
 * @property num The value of the card.
 */
enum class CardValue(val num: String) {
    TWO("-2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("10"),
    QUEEN("10"),
    KING("10"),
    ACE("1"),
    ;

    /**
     * Provides utility functions for working with [CardValue].
     */
    companion object {

        /**
         * A set of values for a reduced set of 4x8=32 cards (starting with the 7)
         */
        fun shortDeck(): Set<CardValue> {
            return EnumSet.of(ACE, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING)
        }

    }




}