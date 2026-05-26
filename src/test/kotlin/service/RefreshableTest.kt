package service

/**
 * Test class for [Refreshable].
 *
 * This class is used in unit tests to verify that the correct refresh
 * methods are triggered by service methods.
 * The refreshes set a boolean flag that can be checked in tests.
 *
 * @property rootService The [RootService] used to register this refreshable
 */
class RefreshableTest(val rootService: RootService): Refreshable {
    var refreshAfterGameStart: Boolean = false
        private set

    var refreshAfterGameEnd: Boolean = false
        private set

    var refreshAfterFlipCard: Boolean = false
        private set

    var refreshAfterPlaceCard: Boolean = false
        private set

    var refreshAfterHandPlace: Boolean = false
        private set

    var refreshAfterDiscardTake: Boolean = false
        private set

    var refreshAfterDiscardPlace: Boolean = false
        private set

    var refreshAfterDeckTake: Boolean = false
        private set

    var refreshAfterRowRemove: Boolean = false
        private set

    var refreshAfterEndTurn: Boolean = false
        private set

    /** Resets all flags to false. */
    fun reset() {
        refreshAfterGameStart = false
        refreshAfterGameEnd = false
        refreshAfterFlipCard = false
        refreshAfterPlaceCard = false
        refreshAfterHandPlace = false
        refreshAfterDiscardTake = false
        refreshAfterDiscardPlace = false
        refreshAfterDeckTake = false
        refreshAfterRowRemove = false
        refreshAfterEndTurn = false
    }

    override fun refreshAfterGameStart() {
        refreshAfterGameStart = true
    }

    override fun refreshAfterGameEnd() {
        refreshAfterGameEnd = true
    }

    override fun refreshAfterFlipCard(slot: Int) {
        refreshAfterFlipCard = true
    }

    override fun refreshAfterPlaceCard(slot: Int) {
        refreshAfterPlaceCard = true
    }

    override fun refreshAfterHandChange() {
        refreshAfterHandPlace = true
    }

    override fun refreshAfterDiscardTake() {
        refreshAfterDiscardTake = true
    }

    override fun refreshAfterDiscardPlace() {
        refreshAfterDiscardPlace = true
    }

    override fun refreshAfterDeckTake() {
        refreshAfterDeckTake = true
    }

    override fun refreshAfterRowRemove(slots: IntArray) {
        refreshAfterRowRemove = true
    }

    override fun refreshAfterEndTurn() {
        refreshAfterEndTurn = true
    }
}
