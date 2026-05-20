package service

import kotlin.test.*

/** Test class for [GameService.startGame]. */
class StartGameTest {
    private lateinit var rootService: RootService
    private lateinit var refreshableTest: RefreshableTest

    /** Setups a new game with [RefreshableTest], a new [RootService]. */
    @BeforeTest
    fun setup() {
        rootService = RootService()
        refreshableTest = RefreshableTest(rootService)
        rootService.gameService.addRefreshable(refreshableTest)
        refreshableTest.reset()
    }

    /** Tests if the game setup with a normal deck is working correctly. */
    @Test
    fun testStartGameWithDeck() {
        rootService.gameService.startGame(
            listOf("PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour"),
            randomOrder = false,
            testMode = false
        )
        val game = requireNotNull(rootService.currentGame) { "Game not found" }
        assertEquals(4, game.players.size)
        assertEquals(1, game.discard.size)
        assertEquals(27, game.deck.size)
        for (player in game.players) {
            assertEquals(6, player.slots.size)
            assertTrue(player.slots.all { it != null })
        }
        assertEquals(0, game.currentPlayer)
    }

    /** Tests if the game setup with a short deck is working correctly. */
    @Test
    fun testStartGameWithShortDeck() {
        rootService.gameService.startGame(
            listOf("PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour"),
            randomOrder = false,
            testMode = true
        )
        val game = requireNotNull(rootService.currentGame) { "Game not found" }
        assertEquals(4, game.players.size)
        assertEquals(1, game.discard.size)
        assertEquals(27, game.deck.size)
        for (player in game.players) {
            assertEquals(6, player.slots.size)
            assertTrue(player.slots.all { it != null })
        }
        assertEquals(0, game.currentPlayer)
    }

    /** Tests if the game setup with five player is correctly throwing an [IllegalStateException] */
    @Test
    fun testTooManyPlayers() {
        assertFailsWith<IllegalStateException> {
            rootService.gameService.startGame(
                listOf(
                    "PlayerOne",
                    "PlayerTwo",
                    "PlayerThree",
                    "PlayerFour",
                    "PlayerFive"
                ), randomOrder = false, testMode = false
            )
        }
    }

    /** Tests if error handling works correctly when game has started already. */
    @Test
    fun testGameStartedAlready() {
        rootService.gameService.startGame(
            playerNames = listOf("PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour"),
            randomOrder = false,
            testMode = false
        )

        assertFailsWith<IllegalStateException> {
            rootService.gameService.startGame(
                playerNames = listOf("PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour"),
                randomOrder = false,
                testMode = false
            )
        }
    }

    /** Tests if the game setup with one player is correctly throwing an [IllegalStateException] */
    @Test
    fun testNotEnoughPlayers() {
        assertFailsWith<IllegalStateException> {
            rootService.gameService.startGame(listOf("PlayerOne"), randomOrder = false, testMode = false)
        }
    }

    /** Tests if the game setup with randomOrder works correctly*/
    @Test
    fun testStartGameWithRandomPlayOrder() {
        val playerList = listOf("PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour")
        rootService.gameService.startGame(playerList, randomOrder = true, testMode = false)
        val gamePlayers = rootService.currentGame!!.players
        assertEquals(gamePlayers.map { it.name }.toSet(), playerList.toSet())
        assertNotEquals(gamePlayers.map { it.name }, playerList)
    }
}
