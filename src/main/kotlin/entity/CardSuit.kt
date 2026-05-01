package entity

/**
 * Enum to distinguish between the four possible suits in a french-suited card game:
 * clubs, spades, hearts, or diamonds
 *
 * @param cardName The name of the suit.
 * @property cardName The name of the suit.
 */
enum class CardSuit (val cardName: String) {
    CLUBS("Clubs ♣"),
    SPADES("Spades ♠"),
    HEARTS("Hearts ♥"),
    DIAMONDS("Diamonds ♦"),
    ;
}