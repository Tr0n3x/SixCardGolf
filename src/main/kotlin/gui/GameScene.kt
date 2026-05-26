package gui

import entity.Card
import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * Represents the game scene with all needed components.
 * This scene has a default size of 1920x1080 pixels and a green background.
 *
 * @property rootService Implements the [RootService].
 */
class GameScene(private val rootService: RootService) : BoardGameScene(
    width = 1920,
    height = 1080,
    background = ColorVisual(108, 168, 59)
),
    Refreshable {

    val game get() = rootService.currentGame ?: throw IllegalStateException("Game not initialized")
    val player get() = game.players[game.currentPlayer]

    // Draw deck
    val deckStack: CardStack<CardView> = CardStack<CardView>(
        posX = (1920 / 2) - 140, // Center: (1920/2)-65 --> BoardGameScene width / 2 - width / 2
        posY = (1080 / 2) - 100, // Center: (1080/2)-100 --> BoardGameScene height / 2 - height / 2
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            rootService.playerActionService.drawCard(false)
        }
    }

    // Discard pile
    val discardStack: CardStack<CardView> = CardStack<CardView>(
        posX = (1920 / 2) + 10, // Center: (1920/2)-65 --> BoardGameScene width / 2 - width / 2
        posY = (1080 / 2) - 100, // Center: (1080/2)-100 --> BoardGameScene height / 2 - height / 2
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            if (game.hand != null) {
                rootService.playerActionService.placeCard(null)
            } else {
                rootService.playerActionService.drawCard(true)
            }
        }
    }

    // Hand slot
    val playerHand: CardStack<CardView> = CardStack(
        posX = (1920 / 2) - 65, // Center: (1920/2)-65 --> BoardGameScene width / 2 - width / 2
        posY = (1080 / 2) + 200, // Center: (1080/2)-100 --> BoardGameScene height / 2 - height / 2
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    )

    // skip Button in main Menu
    val skipButton = Button(
        posX = (1920 / 2) - 65,
        posY = 980,
        width = 130,
        height = 50,
        text = "Skip Turn",
        font = Font(20.0, Color(0x0f141f)),
        visual = ColorVisual(Color(0xc6ff6e))
    ).apply {
        onMouseClicked = {
            rootService.playerActionService.flipCard(null)
        }
    }

    // Here starts playerOne playtable
    private val playerOneNameLabel = Label(
        width = 200,
        height = 40,
        posX = 1920 - 1800,
        posY = 0,
        font = Font(size = 30),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerOneLogLabel = Label(
        width = 600,
        height = 45,
        posX = 1920 - 1800,
        posY = 1080 - 1050,
        font = Font(color = Color.DARK_GRAY, size = 18),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerOneSlotZero: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1800,
        posY = 1080 - 1000,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(0, 0)
        }
    }

    private val playerOneSlotTwo: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1650,
        posY = 1080 - 1000,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(0, 2)
        }
    }

    private val playerOneSlotFour: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1500,
        posY = 1080 - 1000,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(0, 4)
        }
    }

    private val playerOneSlotOne: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1800,
        posY = 1080 - 785,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(0, 1)
        }
    }

    private val playerOneSlotThree: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1650,
        posY = 1080 - 785,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(0, 3)
        }
    }

    private val playerOneSlotFive: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1500,
        posY = 1080 - 785,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(0, 5)
        }
    }

    // Here starts playerTwo playtable
    private val playerTwoNameLabel = Label(
        width = 200,
        height = 40,
        posX = (1920 / 2) + 410,
        posY = 0,
        font = Font(size = 30),
        alignment = Alignment.CENTER_LEFT,
    )

    private val playerTwoLogLabel = Label(
        width = 600,
        height = 45,
        posX = (1920 / 2) + 410,
        posY = 1080 - 1050,
        font = Font(color = Color.DARK_GRAY, size = 18),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerTwoSlotZero: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 550,
        posY = 1080 - 1000,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(1, 0)
        }
    }

    private val playerTwoSlotTwo: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 400,
        posY = 1080 - 1000,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(1, 2)
        }
    }

    private val playerTwoSlotFour: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 250,
        posY = 1080 - 1000,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(1, 4)
        }
    }

    private val playerTwoSlotOne: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 550,
        posY = 1080 - 785,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(1, 1)
        }
    }

    private val playerTwoSlotThree: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 400,
        posY = 1080 - 785,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(1, 3)
        }
    }

    private val playerTwoSlotFive: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 250,
        posY = 1080 - 785,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(1, 5)
        }
    }

    // Here starts playerThree playtable
    private val playerThreeNameLabel = Label(
        width = 200,
        height = 40,
        posX = 1920 - 1800,
        posY = 1080 - 550,
        font = Font(size = 30),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerThreeLogLabel = Label(
        width = 600,
        height = 45,
        posX = 1920 - 1800,
        posY = 1080 - 515,
        font = Font(color = Color.DARK_GRAY, size = 18),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerThreeSlotZero: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1800,
        posY = 1080 - 250,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(2, 0)
        }
    }

    private val playerThreeSlotTwo: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1650,
        posY = 1080 - 250,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(2, 2)
        }
    }

    private val playerThreeSlotFour: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1500,
        posY = 1080 - 250,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(2, 4)
        }
    }

    private val playerThreeSlotOne: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1800,
        posY = 1080 - 465,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(2, 1)
        }
    }

    private val playerThreeSlotThree: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1650,
        posY = 1080 - 465,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(2, 3)
        }
    }

    private val playerThreeSlotFive: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 1500,
        posY = 1080 - 465,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(2, 5)
        }
    }

    // Here starts playerFour playtable
    private val playerFourNameLabel = Label(
        width = 200,
        height = 40,
        posX = (1920 / 2) + 410,
        posY = 1080 - 550,
        font = Font(size = 30),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerFourLogLabel = Label(
        width = 600,
        height = 45,
        posX = (1920 / 2) + 410,
        posY = 1080 - 515,
        font = Font(color = Color.DARK_GRAY, size = 18),
        alignment = Alignment.CENTER_LEFT
    )

    private val playerFourSlotZero: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 400,
        posY = 1080 - 465,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(3, 0)
        }
    }

    private val playerFourSlotTwo: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 550,
        posY = 1080 - 465,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(3, 2)
        }
    }

    private val playerFourSlotFour: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 250,
        posY = 1080 - 465,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(3, 4)
        }
    }

    private val playerFourSlotOne: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 550,
        posY = 1080 - 250,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(3, 1)
        }
    }

    private val playerFourSlotThree: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 400,
        posY = 1080 - 250,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(3, 3)
        }
    }

    private val playerFourSlotFive: CardStack<CardView> = CardStack<CardView>(
        posX = 1920 - 250,
        posY = 1080 - 250,
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            onSlotClick(3, 5)
        }
    }

    // Here starts the scoreBoard
    private val scoreboardBackground = Label(
        posX = 660,
        posY = 340,
        width = 600,
        height = 400,
        visual = ColorVisual(240, 240, 240)
    ).apply {
        isVisible = false
    }

    private val scoreboardLabel = Label(
        posX = (1920 / 2) - 140,
        posY = 360,
        width = 250,
        height = 50,
        text = "Scoreboard",
        font = Font(size = 30),
        alignment = Alignment.CENTER
    ).apply {
        isVisible = false
    }

    private val scoreLabels = listOf(
        Label(posX = 760, posY = 420, width = 300, height = 40, text = "", alignment = Alignment.CENTER),
        Label(posX = 760, posY = 470, width = 300, height = 40, text = "", alignment = Alignment.CENTER),
        Label(posX = 760, posY = 520, width = 300, height = 40, text = "", alignment = Alignment.CENTER),
        Label(posX = 760, posY = 570, width = 300, height = 40, text = "", alignment = Alignment.CENTER)
    )

    // Group player slots for easier access
    private val playerOneSlots = listOf(
        playerOneSlotZero,
        playerOneSlotOne,
        playerOneSlotTwo,
        playerOneSlotThree,
        playerOneSlotFour,
        playerOneSlotFive
    )
    private val playerTwoSlots = listOf(
        playerTwoSlotZero,
        playerTwoSlotOne,
        playerTwoSlotTwo,
        playerTwoSlotThree,
        playerTwoSlotFour,
        playerTwoSlotFive
    )
    private val playerThreeSlots = listOf(
        playerThreeSlotZero,
        playerThreeSlotOne,
        playerThreeSlotTwo,
        playerThreeSlotThree,
        playerThreeSlotFour,
        playerThreeSlotFive
    )
    private val playerFourSlots = listOf(
        playerFourSlotZero,
        playerFourSlotOne,
        playerFourSlotTwo,
        playerFourSlotThree,
        playerFourSlotFour,
        playerFourSlotFive
    )

    private val allPlayerSlots = listOf(playerOneSlots, playerTwoSlots, playerThreeSlots, playerFourSlots)

    // Group player names and logs for easier access
    val nameLabels = listOf(
        playerOneNameLabel,
        playerTwoNameLabel,
        playerThreeNameLabel,
        playerFourNameLabel
    )

    val logLabels = listOf(
        playerOneLogLabel,
        playerTwoLogLabel,
        playerThreeLogLabel,
        playerFourLogLabel
    )

    // Map cards in a BidirectionalMap
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    // Card Image Loader for each refresh method
    private val cardImageLoader = CardImageLoader()

    private val scoresScene = ScoresScene()

    /**
     * Initializes the scene by setting the background color and adding the label.
     */
    init {
        addComponents(deckStack)
        addComponents(discardStack)
        addComponents(playerHand)
        addComponents(skipButton)

        addComponents(playerOneNameLabel)
        addComponents(playerOneLogLabel)
        addComponents(playerOneSlotZero)
        addComponents(playerOneSlotOne)
        addComponents(playerOneSlotTwo)
        addComponents(playerOneSlotThree)
        addComponents(playerOneSlotFour)
        addComponents(playerOneSlotFive)

        addComponents(playerTwoLogLabel)
        addComponents(playerTwoNameLabel)
        addComponents(playerTwoSlotZero)
        addComponents(playerTwoSlotOne)
        addComponents(playerTwoSlotTwo)
        addComponents(playerTwoSlotThree)
        addComponents(playerTwoSlotFour)
        addComponents(playerTwoSlotFive)

        addComponents(playerThreeLogLabel)
        addComponents(playerThreeNameLabel)
        addComponents(playerThreeSlotZero)
        addComponents(playerThreeSlotOne)
        addComponents(playerThreeSlotTwo)
        addComponents(playerThreeSlotThree)
        addComponents(playerThreeSlotFour)
        addComponents(playerThreeSlotFive)

        addComponents(playerFourLogLabel)
        addComponents(playerFourNameLabel)
        addComponents(playerFourSlotZero)
        addComponents(playerFourSlotOne)
        addComponents(playerFourSlotTwo)
        addComponents(playerFourSlotThree)
        addComponents(playerFourSlotFour)
        addComponents(playerFourSlotFive)

        addComponents(scoreboardBackground)
        addComponents(scoreboardLabel)
        addComponents(scoreLabels)
    }

    /** Refreshes the deck,discard pile, player card slots, card slot when the game has started. */
    override fun refreshAfterGameStart() {
        val game = rootService.currentGame ?: return

        // Clear gui
        cardMap.clear()
        deckStack.clear()
        discardStack.clear()
        allPlayerSlots.flatten().forEach { it.clear() }

        // Update deck
        initializeStackView(game.deck, deckStack, cardImageLoader)

        // Update discard
        initializeStackView(game.discard, discardStack, cardImageLoader)

        // Update player slots
        game.players.forEachIndexed { playerIndex, player ->
            player.slots.forEachIndexed { slotIndex, card ->
                if (card != null) {
                    val cardView = CardView(
                        width = 130,
                        height = 200,
                        front = cardImageLoader.frontImageFor(card.suit, card.value),
                        back = cardImageLoader.backImage
                    )

                    showCard(card, cardView)

                    allPlayerSlots[playerIndex][slotIndex].clear()
                    allPlayerSlots[playerIndex][slotIndex].add(cardView)
                    cardMap.add(card to cardView)
                }
            }
        }

        // Player Names
        game.players.forEachIndexed { index, player ->
            nameLabels[index].text = player.name
        }

        highlightName()
    }

    /** Refreshes the log when a players turn has ended. */
    override fun refreshAfterEndTurn() {
        val game = rootService.currentGame ?: return
        game.players.forEachIndexed { index, player ->
            logLabels[index].text = player.lastAction
        }

        highlightName()
    }

    /** Refreshes the hand when a card has been placed in or removed from the hand slot */
    override fun refreshAfterHandChange() {
        val game = rootService.currentGame ?: return
        val card = game.hand
        playerHand.clear()

        if (card != null) {
            val cardView = CardView(
                width = 130,
                height = 200,
                front = cardImageLoader.frontImageFor(card.suit, card.value),
                back = cardImageLoader.backImage
            )

            playerHand.add(cardView)
            cardMap.add(card to cardView)
            showCard(game.hand, cardView)
        }
    }

    /**
     * Refreshes the discard pile when a card has been taken from discard pile.
     * Same function as [refreshAfterDiscardPlace], so refactored into [refreshAfterDiscardChange]
     */
    override fun refreshAfterDiscardTake() {
        val game = rootService.currentGame ?: return
        if (game.discard.isNotEmpty()) {
            refreshAfterDiscardChange()
        } else {
            discardStack.clear()
        }

    }

    /**
     * Refreshes the discard pile when a card has been taken from discard pile.
     * Same function as [refreshAfterDiscardTake], so refactored into [refreshAfterDiscardChange]
     */
    override fun refreshAfterDiscardPlace() {
        refreshAfterDiscardChange()
    }

    /** Refreshes the deck when a card from deck has been taken. */
    override fun refreshAfterDeckTake() {
        val game = rootService.currentGame ?: return
        if (game.deck.isEmpty()) {
            deckStack.clear()
        }
    }

    /** Refreshes the slots of a certain row when it has been removed. */
    override fun refreshAfterRowRemove(slots: IntArray) {
        val game = rootService.currentGame ?: return
        val playerIndex = game.currentPlayer

        slots.forEach { slotIndex ->
            allPlayerSlots[playerIndex][slotIndex].clear()
        }
    }

    /** Refreshes a certain player card slot when a card has been placed/swapped. */
    override fun refreshAfterPlaceCard(slot: Int) {
        val game = rootService.currentGame ?: return
        val playerIndex = game.currentPlayer
        val card = game.players[playerIndex].slots[slot]

        val slotView = allPlayerSlots[playerIndex][slot]
        slotView.clear()

        if (card != null) {
            val cardView = CardView(
                width = 130,
                height = 200,
                front = cardImageLoader.frontImageFor(card.suit, card.value),
                back = cardImageLoader.backImage
            )
            showCard(card, cardView)
            slotView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    /** Refreshes a certain card slot when a card has been flipped. */
    override fun refreshAfterFlipCard(slot: Int) {
        val game = rootService.currentGame ?: return
        val playerIndex = game.currentPlayer
        val card = game.players[playerIndex].slots[slot] ?: return

        val slotView = allPlayerSlots[playerIndex][slot]
        slotView.clear()

        val cardView = CardView(
            width = 130,
            height = 200,
            front = cardImageLoader.frontImageFor(card.suit, card.value),
            back = cardImageLoader.backImage
        )

        showCard(card, cardView)

        slotView.add(cardView)
        cardMap.add(card to cardView)
    }

    /** Refreshes the scoreboard after the game has ended. */
    override fun refreshAfterGameEnd() {
        val game = rootService.currentGame ?: return

        val sortedPlayers = scoresScene.getSortedPlayers(game.players)

        sortedPlayers.forEachIndexed { i, player ->
            scoreLabels[i].text = "${player.name}: ${player.score}"
            scoreLabels[i].isVisible = true
        }

        scoreboardBackground.isVisible = true
        scoreboardLabel.isVisible = true
    }

    /** Handles the click on a players card slot */
    private fun onSlotClick(thisPlayer: Int, slot: Int) {
        if (game.currentPlayer != thisPlayer) return

        val card = player.slots[slot] ?: return

        if (game.hand != null) {
            rootService.playerActionService.placeCard(slot)
        } else if (card.faceDown) {
            rootService.playerActionService.flipCard(slot)
        }
    }

    /** Actual refresh method for [refreshAfterDiscardPlace] and [refreshAfterDiscardTake]*/
    private fun refreshAfterDiscardChange() {
        val game = rootService.currentGame ?: return
        val card = game.discard.last()
        discardStack.clear()

        val cardView = CardView(
            width = 130,
            height = 200,
            front = cardImageLoader.frontImageFor(card.suit, card.value),
            back = cardImageLoader.backImage
        )
        showCard(game.hand, cardView)
        discardStack.add(cardView)
    }

    /** Shows the card front or back in gui. */
    private fun showCard(card: Card?, cardView: CardView) {
        if (card?.faceDown == true) {
            cardView.showBack()
        } else {
            cardView.showFront()
        }
    }

    /** Highlights [entity.SixCardGolfGame.currentPlayer] name */
    private fun highlightName() {
        val game = rootService.currentGame ?: return
        nameLabels.forEachIndexed { index, label ->
            if (index == game.currentPlayer) {
                label.font = Font(size = 30, color = Color.YELLOW)
            } else {
                label.font = Font(size = 30, color = Color.BLACK)      // normal / transparent
            }
        }
    }


    /**
     * clears [stackView], adds a new [CardView] for each
     * element of [stack] onto it, and adds the newly created view/card pair
     * to the global [cardMap].
     */
    private fun initializeStackView(
        stack: List<Card>,
        stackView: CardStack<CardView>,
        cardImageLoader: CardImageLoader
    ) {
        stackView.clear()
        stack.reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = cardImageLoader.frontImageFor(card.suit, card.value),
                back = cardImageLoader.backImage
            )

            if (card.faceDown) {
                cardView.showBack()
            } else {
                cardView.showFront()
            }

            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }
}
