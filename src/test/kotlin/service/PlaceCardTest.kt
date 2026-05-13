package service

import entity.Action
import kotlin.test.*

class PlaceCardTest {
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


    /** Tests if placeCard will be stopped when the game logic does not allow this action. */
    @Test
    fun testWrongAction() {
        val game = requireNotNull(rootService.currentGame)
        game.currentAction = Action.FLIP

        assertFailsWith<IllegalStateException> {
            rootService.playerActionService.placeCard(0)
        }
    }

    /** Tests if placeCard will be stopped when the player tries to discard after drawing a card from discard. */
    @Test
    fun testDiscardAfterDrawFromDiscard() {
        val game = requireNotNull(rootService.currentGame)
        game.currentAction = Action.DRAW_DISCARD

        assertFailsWith<IllegalStateException> {
            rootService.playerActionService.placeCard(null)
        }
    }

    /** Tests if placeCard works correctly when the player tries to discard after drawing a card from deck. */
    @Test
    fun testDiscardAfterDrawFromDeck() {
        val game = requireNotNull(rootService.currentGame)

        rootService.playerActionService.drawCard(false)
        val drawnCard = game.hand

        rootService.playerActionService.placeCard(null)

        assertNull(game.hand)
        assertEquals(drawnCard, game.discard.last())
        assertEquals(Action.FLIP, game.currentAction)
        assertTrue(refreshableTest.refreshAfterDiscardPlace)
        assertTrue(refreshableTest.refreshAfterHandPlace)
    }

    /** Tests if the replacing card path works correctly. */
    @Test
    fun testReplacingCard() {
        val game = requireNotNull(rootService.currentGame)
        val player = game.players[game.currentPlayer]
        val oldCard = player.slots[0]

        rootService.playerActionService.drawCard(false)
        val drawnCard = game.hand

        rootService.playerActionService.placeCard(0)

        assertEquals(drawnCard, player.slots[0])
        assertEquals(oldCard, game.discard.last())
        assertNull(game.hand)
        assertTrue(refreshableTest.refreshAfterDiscardPlace)
        assertTrue(refreshableTest.refreshAfterPlaceCard)
        assertTrue(refreshableTest.refreshAfterHandPlace)
    }
}