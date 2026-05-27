package service

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the GUI classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * GUI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 */
interface Refreshable {
    /** Refreshes the GUI after the game started. */
    fun refreshAfterGameStart() {}

    /** Refreshes the GUI after the game ended. */
    fun refreshAfterGameEnd() {}

    /** Refreshes the GUI after a card has been flipped. */
    fun refreshAfterFlipCard(slot: Int) {}

    /** Refreshes the GUI after a card has been placed in your slots. */
    fun refreshAfterPlaceCard(slot: Int) {}

    /** Refreshes the GUI after a card has been placed in the hand slot. */
    fun refreshAfterHandChange() {}

    /** Refreshes the GUI after a card has been taken from discard pile. */
    fun refreshAfterDiscardTake() {}

    /** Refreshes the GUI after a card has been placed on discard pile. */
    fun refreshAfterDiscardPlace() {}

    /** Refreshes the GUI after a card has been taken from deck. */
    fun refreshAfterDeckTake() {}

    /** Refreshes the GUI after a row of slots have been removed. */
    fun refreshAfterRowRemove(slots: IntArray) {}

    /** Refreshes the GUI after a players turn has ended. */
    fun refreshAfterEndTurn() {}

    /** Refreshes the GUI after a return to menu has been called. */
    fun refreshAfterReturnToMenu() {}
}
