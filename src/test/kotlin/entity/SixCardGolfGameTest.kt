package entity

import kotlin.test.*

class SixCardGolfGameTest {

    /** Checks if all initial values are correctly initialized. */
    @Test
    fun gameInitialValues() {
        val players = mutableListOf(Player("PlayerOne"), Player("PlayerTwo"))
        val game = SixCardGolfGame(players)

        assertEquals(players, game.players)
        assertNull(game.finalPlays)
        assertEquals(0, game.currentPlayer)
        assertTrue(game.deck.isEmpty())
        assertTrue(game.discard.isEmpty())
        assertNull(game.currentAction)
        assertNull(game.hand)
    }
}
