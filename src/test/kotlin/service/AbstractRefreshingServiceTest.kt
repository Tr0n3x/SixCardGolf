package service

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * This class tests the functionality of the [AbstractRefreshingService] class.
 */
class AbstractRefreshingServiceTest {

    /**
     * This refreshable is initialized in the [setUp] function hence it is a late-initialized property.
     */
    private lateinit var testRefreshable: Refreshable

    /**
     * This abstractRefreshingService is initialized in the [setUp] function hence it is a late-initialized property.
     */
    private lateinit var abstractRefreshingService: AbstractRefreshingService

    /**
     * Initialize service to set up the test environment. This function is executed before every test.
     */
    @BeforeTest
    fun setUp() {
        testRefreshable = object : Refreshable {
            override fun refreshAfterGameStart() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterGameEnd() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterFlipCard() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterPlaceCard() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterHandPlace() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterDiscardTake() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterDiscardPlace() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterDeckTake() {
                TODO("Not yet implemented")
            }

            override fun refreshAfterRowRemove(slots: Array<Int>) {
                TODO("Not yet implemented")
            }

            override fun refreshAfterEndTurn() {
                TODO("Not yet implemented")
            }
        }
        abstractRefreshingService = object : AbstractRefreshingService() {}
        abstractRefreshingService.addRefreshable(testRefreshable)
    }

    /**
     * Tests if the refreshable is notified and is the correct one.
     */
    @Test
    fun testIfRefreshableIsNotified() {
        var refreshWasCalled = false
        var isTestRefreshable = false

        /**
         * This function is used to test if the refresh method was called on the test refreshable.
         */
        fun Refreshable.refreshForTesting() {
            refreshWasCalled = true
            isTestRefreshable = this === testRefreshable
        }

        abstractRefreshingService.onAllRefreshables { this.refreshForTesting() }

        assertTrue(refreshWasCalled, "The refresh method should have been called.")
        assertTrue(isTestRefreshable, "The refresh method should have been called on the test refreshable.")
    }
}
