package service

import entity.Action
import entity.Card
import entity.CardSuit
import entity.CardValue
import kotlin.test.*

/** Test class for [PlayerActionService.flipCard]. */
class FlipCardTest {
    private lateinit var rootService: RootService
    private lateinit var refreshableTest: RefreshableTest

    /** Setups a new game with [RefreshableTest], a new [RootService]. */
    @BeforeTest
    fun setup() {
        rootService = RootService()
        refreshableTest = RefreshableTest(rootService)
        rootService.playerActionService.addRefreshable(refreshableTest)
        rootService.gameService.addRefreshable(refreshableTest)
        rootService.gameService.startGame(
            listOf("PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour"),
            randomOrder = false,
            testMode = false
        )
        refreshableTest.reset()
    }

    /** Tests if flipCard will be stopped when the game logic does not allow this action. */
    @Test
    fun testWrongAction() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1
        game.currentAction = Action.DRAW_DECK

        assertFailsWith<IllegalStateException> {
            rootService.playerActionService.flipCard(0)
        }
    }

    /** Tests if flipCard will be stopped when the player tries to skip on first flip. */
    @Test
    fun testSkipOnFirstFlip() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1

        game.currentAction = Action.FLIP
        assertFailsWith<IllegalStateException> {
            rootService.playerActionService.flipCard(null)
        }
    }

    /** Tests if flipCard throws the correct exception if method is called with null in round 0 */
    @Test
    fun testIllegalArgumentRoundZero() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 0
        game.currentAction = Action.FLIP

        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.flipCard(null)
        }
    }

    /** Tests if flipCard throws the correct exception if a nonexistent slot has been called */
    @Test
    fun testIllegalArgument() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 0

        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.flipCard(6)
        }
    }
    /** Tests if flipCard works correctly when a slot is given to flip */
    @Test
    fun testFlipCard() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1

        game.currentAction = Action.FLIP

        assertTrue { game.players[game.currentPlayer].slots[0]?.faceDown == true }
        rootService.playerActionService.flipCard(0)
        assertTrue { game.players[game.currentPlayer].slots[0]?.faceDown == false }
        assertTrue { refreshableTest.refreshAfterFlipCard }
    }

    /** Tests if finishMove happens after the second flip action. */
    @Test
    fun testFinishMoveAfterTwoFlips() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1

        game.currentAction = Action.FLIP

        rootService.playerActionService.flipCard(0)
        rootService.playerActionService.flipCard(1)

        assertTrue(refreshableTest.refreshAfterEndTurn)
    }

    /** Tests if removeRow happens after a flip shows three cards of same value title in row. */
    @Test
    fun testRemoveRowAfterFlip() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1

        val player = game.players[game.currentPlayer]
        game.currentAction = Action.FLIP

        player.slots[0] = Card(CardSuit.HEARTS, CardValue.TWO, true)
        player.slots[2] = Card(CardSuit.DIAMONDS, CardValue.TWO, false)
        player.slots[4] = Card(CardSuit.SPADES, CardValue.TWO, false)

        rootService.playerActionService.flipCard(0)

        assertTrue(refreshableTest.refreshAfterRowRemove)
        assertNull(player.slots[0])
        assertNull(player.slots[2])
        assertNull(player.slots[4])
    }

    /** Tests if final Round will start after flipping last card */
    @Test
    fun testFinalRoundAfterFlip() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1

        val player = game.players[game.currentPlayer]
        game.currentAction = Action.FLIP

        player.slots[0] = Card(CardSuit.HEARTS, CardValue.TWO, true)
        player.slots[1] = Card(CardSuit.DIAMONDS, CardValue.ACE, false)
        player.slots[2] = Card(CardSuit.SPADES, CardValue.THREE, false)
        player.slots[3] = Card(CardSuit.DIAMONDS, CardValue.THREE, false)
        player.slots[4] = Card(CardSuit.SPADES, CardValue.KING, false)
        player.slots[5] = Card(CardSuit.SPADES, CardValue.QUEEN, false)
        rootService.playerActionService.flipCard(0)

        assertTrue(game.finalRound)
        assertEquals(game.players.size - 1, game.finalPlays)
        assertTrue(refreshableTest.refreshAfterFlipCard)
    }

    /** Tests if endGame starts when all player are finished. */
    @Test
    fun testEndGameAfterFlip() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 1

        val player = game.players[game.currentPlayer]

        game.currentAction = Action.FLIP
        game.finalRound = true
        game.finalPlays = 1

        player.slots[0] = Card(CardSuit.HEARTS, CardValue.TWO, true)
        player.slots[1] = Card(CardSuit.DIAMONDS, CardValue.ACE, false)
        player.slots[2] = Card(CardSuit.SPADES, CardValue.THREE, false)
        player.slots[3] = Card(CardSuit.DIAMONDS, CardValue.FOUR, false)
        player.slots[4] = Card(CardSuit.SPADES, CardValue.KING, false)
        player.slots[5] = Card(CardSuit.SPADES, CardValue.QUEEN, false)

        rootService.playerActionService.flipCard(0)

        assertTrue(refreshableTest.refreshAfterGameEnd)
    }

    /** Tests if the flip works in initial starting round. */
    @Test
    fun testInitialFlip() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 0
        val card = game.players[0].slots[0]!!

        assertTrue(card.faceDown)
        rootService.playerActionService.flipCard(0)
        assertFalse(card.faceDown)
    }

    /** Tests if the player changes after two flips in starting round. */
    @Test
    fun testInitialFlipPlayerChange() {
        val game = requireNotNull(rootService.currentGame)
        game.round = 0

        assertEquals(0,game.currentPlayer)

        rootService.playerActionService.flipCard(0)
        rootService.playerActionService.flipCard(1)

        assertEquals(1, game.currentPlayer)
    }
}
