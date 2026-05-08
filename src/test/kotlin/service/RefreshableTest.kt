package service

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

    override fun refreshAfterFlipCard() {
        refreshAfterFlipCard = true
    }

    override fun refreshAfterPlaceCard() {
        refreshAfterPlaceCard = true
    }

    override fun refreshAfterHandPlace() {
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

    override fun refreshAfterRowRemove(slots: Array<Int>) {
        refreshAfterRowRemove(slots)
    }

    override fun refreshAfterEndTurn() {
        refreshAfterEndTurn = true
    }
}