package entity

/**
 * Enum to distinguish between the four possible suits in a french-suited card game:
 * clubs, spades, hearts, or diamonds
 *
 * @property suitName The name of the suit.
 */
enum class CardSuit(val suitName: String) {
    CLUBS("Clubs ♣"),
    SPADES("Spades ♠"),
    HEARTS("Hearts ♥"),
    DIAMONDS("Diamonds ♦"),
    ;
}
