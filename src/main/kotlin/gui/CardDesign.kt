package gui

/**
 * This Class adds different card designs.
 *
 * @property file The file as string to be used as card design.
 */
enum class CardDesign(val file: String) {
    CLASSIC("card_deck.png"),
    MODERN("card_deck_modern.png");

    /** Iterates to the next object. */
    fun next(): CardDesign {
        val values = entries
        return values[(ordinal + 1) % values.size]
    }

    /** Iterates to the previous object. */
    fun previous(): CardDesign {
        val values = entries
        return values[(ordinal - 1 + values.size) % values.size]
    }
}
