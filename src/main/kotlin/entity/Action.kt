package entity

/**
 * Enum to distinguish between the three available actions:
 * FLIP, DRAW_DECK, DRAW_DISCARD.
 *
 * [FLIP] does flip a card.
 * [DRAW_DECK] does draw a card from the deck.
 * [DRAW_DISCARD] does draw a card from the discard pile.
 */
enum class Action {
    FLIP,
    DRAW_DECK,
    DRAW_DISCARD
}
