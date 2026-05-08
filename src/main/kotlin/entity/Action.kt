package entity

/**
 * Enum to distinguish between the three available actions:
 * FLIP, DRAW_DECK, DRAW_DISCARD.
 *
 * [FLIP] flips a card.
 * [DRAW_DECK] draws a card from the deck.
 * [DRAW_DISCARD] draws a card from the discard pile.
 */
enum class Action {
    FLIP,
    DRAW_DECK,
    DRAW_DISCARD
}
