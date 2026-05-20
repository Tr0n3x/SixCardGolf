package service

import entity.Action
import kotlin.test.*

/** Test class for [PlayerActionService.drawCard]. */
class DrawCardTest {
    private lateinit var rootService: RootService
    private lateinit var refreshableTest: RefreshableTest

    /** Setups a new game with [RefreshableTest], a new [RootService]. */
    @BeforeTest
    fun setup() {
        rootService = RootService()
        refreshableTest = RefreshableTest(rootService)
        rootService.playerActionService.addRefreshable(refreshableTest)
        rootService.gameService.startGame(
            listOf("PlayerOne", "PlayerTwo","PlayerThree", "PlayerFour"),
            randomOrder = false,
            testMode = false
        )
        refreshableTest.reset()
    }

    /** Tests if drawCard will be stopped when the game logic does not allow this action. */
    @Test
    fun testWrongAction() {
        val game = requireNotNull(rootService.currentGame)
        game.currentAction = Action.FLIP

        assertFailsWith<IllegalStateException> {
            rootService.playerActionService.drawCard(true)
        }
    }

    /** Tests if drawCard will be stopped when the game logic does not allow this action. */
    @Test
    fun testDiscard() {
        val game = requireNotNull(rootService.currentGame)
        val card = game.discard.last()
        game.round = 1
        rootService.playerActionService.drawCard(true)

        assertEquals(card,game.hand)
        assertEquals(Action.DRAW_DISCARD, game.currentAction)
        assertTrue(refreshableTest.refreshAfterDiscardTake)
        assertTrue(refreshableTest.refreshAfterHandPlace)
    }

    /** Tests if drawCard will be stopped when the game logic does not allow this action. */
    @Test
    fun testDeck() {
        val game = requireNotNull(rootService.currentGame)
        val card = game.deck.last()
        card.flip()
        game.round = 1

        rootService.playerActionService.drawCard(false)

        assertEquals(card,game.hand)
        assertEquals(Action.DRAW_DECK, game.currentAction)
        assertTrue(refreshableTest.refreshAfterDeckTake)
        assertTrue(refreshableTest.refreshAfterHandPlace)
    }

    /** Tests if error handling works correctly when no game has started yet. */
    @Test
    fun testNoGameStarted() {
        rootService.currentGame = null
        assertFailsWith<IllegalStateException> {
            rootService.playerActionService.drawCard(true)
        }
    }
}
