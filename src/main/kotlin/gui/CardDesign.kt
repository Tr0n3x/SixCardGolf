package gui

enum class CardDesign(val file: String) {
    CLASSIC("card_deck.png"),
    MODERN("card_deck_modern.png");

    fun next(): CardDesign {
        val values = entries
        return values[(ordinal + 1) % values.size]
    }

    fun previous(): CardDesign {
        val values = entries
        return values[(ordinal - 1 + values.size) % values.size]
    }
}
