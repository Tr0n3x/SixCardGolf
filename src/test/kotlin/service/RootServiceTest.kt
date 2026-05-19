package service

import kotlin.test.*

/** Test class for [RootService] */
class RootServiceTest {

    private val rootService = RootService()

    /** Private Class to make the tests work */
    private class TestRefreshable : Refreshable {
        override fun refreshAfterGameEnd() {}
    }

    /** Tests if AddRefreshable works without error. */
    @Test
    fun testAddRefreshable() {
        val refreshable = TestRefreshable()
        rootService.addRefreshable(refreshable)
    }

    /** Tests if the refreshables work fine. */
    @Test
    fun testAddRefreshablesWithMultipleRefreshables() {
        val r1 = TestRefreshable()
        val r2 = TestRefreshable()

        rootService.addRefreshables(r1, r2)
    }
}