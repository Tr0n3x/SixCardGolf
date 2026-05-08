package entity

import kotlin.test.*

/** Test class for the entity class Player */
class PlayerTest {

    /** Checks if all initial values are working fine */
    @Test
    fun testPlayerValues() {
        val player = Player("PlayerOne")

        assertEquals("PlayerOne", player.name)
        assertEquals(0, player.score)
        assertEquals("", player.lastAction)
        assertEquals(6, player.slots.size)
        assertTrue(player.slots.all { it == null })
    }
}