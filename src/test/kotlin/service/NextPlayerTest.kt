package service

import kotlin.test.*

/** Test class for [GameService.nextPlayer]. */
class NextPlayerTest {
    private lateinit var rootService: RootService
    private lateinit var refreshableTest: RefreshableTest

    /** Setups a new game with [RefreshableTest], a new [RootService]. */
    @BeforeTest
    fun setup() {
        rootService = RootService()
        refreshableTest = RefreshableTest(rootService)
        rootService.gameService.addRefreshable(refreshableTest)
        rootService.gameService.startGame(
            listOf("PlayerOne", "PlayerTwo","PlayerThree", "PlayerFour"),
            randomOrder = false,
            testMode = false
        )
        refreshableTest.reset()
    }
    /** Tests if the [GameService.nextPlayer] method is working correctly. */
    @Test
    fun testNextPlayer() {
        val game = requireNotNull(rootService.currentGame) { "Game not found" }
        assertEquals(0,game.currentPlayer)
        rootService.gameService.nextPlayer()
        assertEquals(1, game.currentPlayer)
    }

    /** Tests if the [GameService.nextPlayer] method returns to player one after the last player made their move. */
    @Test
    fun testNextPlayerFullRound() {
        val game = requireNotNull(rootService.currentGame) { "Game not found" }
        game.currentPlayer = 3
        assertEquals(3,game.currentPlayer)
        rootService.gameService.nextPlayer()
        assertEquals(0, game.currentPlayer)
    }

    /** Tests if error handling works correctly when the method is called while no game has started. */
    @Test
    fun testNoGameFound() {
        rootService.currentGame = null

        assertFailsWith<IllegalStateException> {
            rootService.gameService.nextPlayer()
        }
    }
}
