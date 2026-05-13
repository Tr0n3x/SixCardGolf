package entity

import kotlin.test.*

/** Test class for the entity class Card*/
class CardTest {

    private val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE, false)
    private val kingOfHearts = Card(CardSuit.HEARTS, CardValue.KING, false)
    private val twoOfDiamonds = Card(CardSuit.DIAMONDS, CardValue.TWO, false)
    private val threeOfClubs = Card(CardSuit.CLUBS,CardValue.THREE,true)
    private val threeOfClubsTwo = Card(CardSuit.CLUBS, CardValue.THREE,true)

    /**
     * Check if toString produces the correct strings for some test cards
     * of all four suits.
     */
    @Test
    fun testToString() {
        assertEquals( "Ace of Spades ♠",aceOfSpades.toString())
        assertEquals("King of Hearts ♥",kingOfHearts.toString())
        assertEquals("Two of Diamonds ♦",twoOfDiamonds.toString())
        assertEquals("Three of Clubs ♣",threeOfClubs.toString())
    }

    /**
     * Check if equals does check for the same object,
     * different object but same card, different cards and null
     */
    @Test
    fun testEquals() {
        assertEquals(twoOfDiamonds, twoOfDiamonds)
        assertEquals(threeOfClubs, threeOfClubsTwo)
        assertNotEquals(kingOfHearts, aceOfSpades)
        assertNotNull(twoOfDiamonds)
    }
    /** Check for consistent and working hashCode */
    @Test
    fun testHashCode() {
        val hash1 = twoOfDiamonds.hashCode()
        val hash2 = aceOfSpades.hashCode()
        val hash3 = twoOfDiamonds.hashCode()

        assertNotEquals(hash1, hash2)
        assertEquals(hash1, hash3)
    }
}
