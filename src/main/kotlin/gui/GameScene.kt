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
import kotlin.system.exitProcess

/**
 * Represents the game scene with all needed components.
 * This scene has a default size of 1920x1080 pixels and a green background.
 *
 * @property rootService Implements the [RootService].
 */
class GameScene(private val rootService: RootService, private val application: SixCardGolfGameApplication) :
    BoardGameScene(
        width = 1920,
        height = 1080,
        background = ColorVisual(108, 168, 59)
    ),
    Refreshable {

    private val game get() = rootService.currentGame ?: throw IllegalStateException("Game not initialized")
    private val player get() = game.players[game.currentPlayer]

    private var drewFromDiscard = false
    private var placedOnDiscard = false
    private var firstFlip = true

    // Options Menu
    private val optionsButton = Button(
        posX = (1920 / 2) - 65,
        posY = 20,
        width = 130,
        height = 50,
        text = "Options",
        font = Font(20, Color.DARK_GRAY, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(200,200,200)
    ).apply {
        onMouseClicked = {
            application.showOptionsMenu()
        }
    }

    // Draw deck
    private val deckStack: CardStack<CardView> = CardStack<CardView>(
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
    private val discardStack: CardStack<CardView> = CardStack<CardView>(
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
    private val playerHand: CardStack<CardView> = CardStack(
        posX = (1920 / 2) - 65, // Center: (1920/2)-65 --> BoardGameScene width / 2 - width / 2
        posY = (1080 / 2) + 200, // Center: (1080/2)-100 --> BoardGameScene height / 2 - height / 2
        height = 200,
        width = 130,
        visual = ColorVisual(255, 255, 255, 50)
    )

    // skip Button in main Menu
    private val skipButton = Button(
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
        width = 700,
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
        width = 700,
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
        width = 700,
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
        width = 700,
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
        Label(
            posX = 760,
            posY = 420,
            width = 300,
            height = 40,
            text = "",
            alignment = Alignment.CENTER,
            font = Font(size = 40, color = Color.YELLOW)
        ).apply { isVisible = false },
        Label(
            posX = 760,
            posY = 470,
            width = 300,
            height = 40,
            text = "",
            alignment = Alignment.CENTER,
            font = Font(size = 30)
        ).apply { isVisible = false },
        Label(
            posX = 760,
            posY = 520,
            width = 300,
            height = 40,
            text = "",
            alignment = Alignment.CENTER,
            font = Font(size = 30)
        ).apply { isVisible = false },
        Label(
            posX = 760,
            posY = 570,
            width = 300,
            height = 40,
            text = "",
            alignment = Alignment.CENTER,
            font = Font(size = 30)
        ).apply { isVisible = false },
    )

    private val scoreboardNewGameButton = Button(
        posX = (1920 / 2) + 100,
        posY = 650,
        width = 130,
        height = 50,
        text = "Main Menu",
        font = Font(20.0, Color(0x0f141f)),
        visual = ColorVisual(Color(136, 221, 136))
    ).apply {
        isVisible = false
        onMouseClicked = {
            rootService.currentGame = null
            application.showMainMenu()
        }
    }

    private val scoreboardExitButton = Button(
        posX = (1920 / 2) - 240,
        posY = 650,
        width = 130,
        height = 50,
        text = "Quit",
        font = Font(20.0, Color(0x0f141f)),
        visual = ColorVisual(Color(221, 136, 136))
    ).apply {
        isVisible = false
        onMouseClicked = {
            exitProcess(0)
        }
    }

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

    // Here start all highlight Labels
    private val deckHighlight = Label(
        posX = deckStack.posX - 4,
        posY = deckStack.posY - 4,
        width = deckStack.width + 8,
        height = deckStack.height + 8,
        visual = ColorVisual(255, 255, 0),
    ).apply { isVisible = false }

    private val discardHighlight = Label(
        posX = discardStack.posX - 4,
        posY = discardStack.posY - 4,
        width = discardStack.width + 8,
        height = discardStack.height + 8,
        visual = ColorVisual(255, 255, 0),
    ).apply { isVisible = false }

    private val skipButtonHighlight = Label(
        posX = skipButton.posX - 4,
        posY = skipButton.posY - 4,
        width = skipButton.width + 8,
        height = skipButton.height + 8,
        visual = ColorVisual(255, 255, 0),
    ).apply { isVisible = false }

    private val slotHighlights = allPlayerSlots.map { playerSlots ->
        playerSlots.map { slot ->
            Label(
                posX = slot.posX - 4,
                posY = slot.posY - 4,
                width = slot.width + 8,
                height = slot.height + 8,
                visual = ColorVisual(255, 255, 0)
            ).apply {
                isVisible = false
            }
        }
    }

    // Group player names and logs for easier access
    private val nameLabels = listOf(
        playerOneNameLabel,
        playerTwoNameLabel,
        playerThreeNameLabel,
        playerFourNameLabel
    )

    private val logLabels = listOf(
        playerOneLogLabel,
        playerTwoLogLabel,
        playerThreeLogLabel,
        playerFourLogLabel
    )

    // Map cards in a BidirectionalMap
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    // Card Image Loader for refresh methods and cardDesigns
    private val cardImageLoader get() = CardImageLoader(rootService.cardDesign)

    private val scoresScene = ScoresScene()

    /**
     * Initializes the scene by adding components.
     */
    init {
        // add highlights
        addComponents(deckHighlight, discardHighlight, skipButtonHighlight)
        slotHighlights.flatten().forEach { addComponents(it) }

        // add game interaction content
        addComponents(deckStack, discardStack, playerHand, skipButton)

        // add player one
        addComponents(
            playerOneNameLabel,
            playerOneLogLabel,
            playerOneSlotZero,
            playerOneSlotOne,
            playerOneSlotTwo,
            playerOneSlotThree,
            playerOneSlotFour,
            playerOneSlotFive
        )

        // add player two
        addComponents(
            playerTwoLogLabel,
            playerTwoNameLabel,
            playerTwoSlotZero,
            playerTwoSlotOne,
            playerTwoSlotTwo,
            playerTwoSlotThree,
            playerTwoSlotFour,
            playerTwoSlotFive
        )

        // add player three
        addComponents(
            playerThreeLogLabel,
            playerThreeNameLabel,
            playerThreeSlotZero,
            playerThreeSlotOne,
            playerThreeSlotTwo,
            playerThreeSlotThree,
            playerThreeSlotFour,
            playerThreeSlotFive
        )

        // add player four
        addComponents(
            playerFourLogLabel,
            playerFourNameLabel,
            playerFourSlotZero,
            playerFourSlotOne,
            playerFourSlotTwo,
            playerFourSlotThree,
            playerFourSlotFour,
            playerFourSlotFive
        )

        // add scoreboard
        addComponents(scoreboardBackground, scoreboardLabel, scoreboardNewGameButton, scoreboardExitButton)
        addComponents(scoreLabels)

        // add options button
        addComponents(optionsButton)
    }

    /** Refreshes the card design. */
    override fun refreshAfterCardDesignChange() {
        val cardLoader = CardImageLoader(rootService.cardDesign)

        cardMap.entries.forEach { (card, view) ->
            view.frontVisual = cardLoader.frontImageFor(card.suit, card.value)
            view.backVisual = cardLoader.backImage
        }
    }

    /** Refreshes the deck,discard pile, player card slots, card slot when the game has started. */
    override fun refreshAfterGameStart() {
        // Clear gui
        cardMap.clear()
        deckStack.clear()
        discardStack.clear()
        allPlayerSlots.flatten().forEach { it.clear() }

        // hide and disable scoreboard elements
        scoreboardBackground.isVisible = false
        scoreboardLabel.isVisible = false
        scoreLabels.forEach { it.isVisible = false }
        scoreboardNewGameButton.isVisible = false
        scoreboardExitButton.isVisible = false

        scoreboardBackground.isDisabled = true
        scoreboardLabel.isDisabled = true
        scoreLabels.forEach { it.isDisabled = true }

        // activate game elements again
        deckStack.isDisabled = false
        discardStack.isDisabled = false
        skipButton.isDisabled = false

        // Reset logs
        logLabels.forEach { it.text = "" }

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

        updatePlayerVisibility()
        highlightName()
        updateInteractionState()
    }

    /** Refreshes the log when a players turn has ended. */
    override fun refreshAfterEndTurn() {
        game.players.forEachIndexed { index, player ->
            logLabels[index].text = player.lastAction
        }
        highlightName()
        drewFromDiscard = false
        placedOnDiscard = false
        firstFlip = true
        updateInteractionState()
    }

    /** Refreshes the hand when a card has been placed in or removed from the hand slot */
    override fun refreshAfterHandChange() {
        playerHand.clear()

        val card = game.hand ?: return
        val cardView = cardMap.forward(card)

        if (cardView.parent != null) {
            cardView.removeFromParent()
        }

        showCard(card, cardView)
        playerHand.add(cardView)
        updateInteractionState()
    }

    /** Refreshes the discard pile when a card has been taken from discard pile. */
    override fun refreshAfterDiscardTake() {
        discardStack.clear()

        game.discard.lastOrNull()?.let { card ->
            val cardView = cardMap.forward(card)
            showCard(card, cardView)
            discardStack.add(cardView)
        }
        drewFromDiscard = true
        updateInteractionState()
    }

    /** Refreshes the discard pile when a card has been taken from discard pile. */
    override fun refreshAfterDiscardPlace() {
        val card = game.discard.lastOrNull() ?: return
        val cardView = cardMap.forward(card)

        if (cardView.parent != null) {
            cardView.removeFromParent()
        }

        showCard(card, cardView)

        discardStack.clear()
        discardStack.add(cardView)
        placedOnDiscard = true
        updateInteractionState()
    }

    /** Refreshes the deck when a card from deck has been taken. */
    override fun refreshAfterDeckTake() {
        if (game.deck.isEmpty()) {
            deckStack.clear()
        }
        updateInteractionState()
    }

    /** Refreshes the slots of a certain row when it has been removed. */
    override fun refreshAfterRowRemove(slots: IntArray) {
        val playerIndex = game.currentPlayer

        slots.forEach { slotIndex ->
            allPlayerSlots[playerIndex][slotIndex].clear()
            allPlayerSlots[playerIndex][slotIndex].isVisible = false
        }
    }

    /** Refreshes a certain player card slot when a card has been placed/swapped. */
    override fun refreshAfterPlaceCard(slot: Int) {
        val playerIndex = game.currentPlayer
        val card = game.players[playerIndex].slots[slot]

        val slotView = allPlayerSlots[playerIndex][slot]

        if (card != null) {
            val cardView = cardMap.forward(card)

            // remove from old container
            if (cardView.parent != null) cardView.removeFromParent()

            // clear slot
            slotView.clear()

            // update card side
            showCard(card, cardView)

            // add to new container
            slotView.add(cardView)
        }
    }

    /** Refreshes a certain card slot when a card has been flipped. */
    override fun refreshAfterFlipCard(slot: Int) {
        val playerIndex = game.currentPlayer
        val card = game.players[playerIndex].slots[slot] ?: return

        val cardView = cardMap.forward(card)
        showCard(card, cardView)

        firstFlip = false
        updateInteractionState()
    }

    /** Refreshes the scoreboard after the game has ended. */
    override fun refreshAfterGameEnd() {

        // deactivate game elements
        deckStack.isDisabled = true
        discardStack.isDisabled = true
        skipButton.isDisabled = true

        game.players.forEach { player ->
            player.slots.forEach { card ->
                if (card != null) {
                    val cardView = cardMap.forward(card)
                    showCard(card, cardView)
                }
            }
        }
        showScoreboard()
    }

    /** Shows scoreboard and deactivates game content */
    private fun showScoreboard() {
        val sortedPlayers = scoresScene.getSortedPlayers(game.players)

        sortedPlayers.forEachIndexed { i, player ->
            scoreLabels[i].text = "${player.name}: ${player.score}"
            scoreLabels[i].isVisible = true
        }

        scoreboardBackground.isVisible = true
        scoreboardLabel.isVisible = true
        scoreboardNewGameButton.isVisible = true
        scoreboardExitButton.isVisible = true

        discardStack.isDisabled = true
        deckStack.isDisabled = true
        skipButton.isDisabled = true

        // deactivate all highlights
        slotHighlights.flatten().forEach { it.isVisible = false }
    }

    /** Updates when [deckStack], [discardStack] and [skipButton] are clickable */
    private fun updateInteractionState() {
        val roundZero = game.round == 0
        val hasHand = game.hand != null

        // Enable and disable stacks
        deckStack.isDisabled = hasHand || roundZero || deckStack.isEmpty() || !firstFlip
        discardStack.isDisabled =
            drewFromDiscard || roundZero || discardStack.isEmpty() || !firstFlip || placedOnDiscard

        skipButton.isDisabled = hasHand || roundZero || firstFlip

        // Highlight stacks
        deckHighlight.isVisible = !deckStack.isDisabled
        discardHighlight.isVisible = !discardStack.isDisabled
        skipButtonHighlight.isVisible = !skipButton.isDisabled

        // Highlight Slots
        allPlayerSlots.forEachIndexed { playerIndex, slots ->

            if (playerIndex >= game.players.size) {
                slots.forEachIndexed { slotIndex, _ ->
                    slotHighlights[playerIndex][slotIndex].isVisible = false
                }
                return@forEachIndexed
            }

            slots.forEachIndexed { slotIndex, _ ->
                val card = game.players[playerIndex].slots[slotIndex]
                val clickable =
                    playerIndex == game.currentPlayer && card != null &&
                            (game.hand != null || card.faceDown)

                slotHighlights[playerIndex][slotIndex].isVisible = clickable
            }
        }
    }

    /** Updates the visibility of the player slots */
    private fun updatePlayerVisibility() {
        val playerCount = game.players.size

        allPlayerSlots.forEachIndexed { index, slots ->
            val visible = index < playerCount

            slots.forEach { it.isVisible = visible }
            slotHighlights[index].forEach { it.isVisible = false }
            nameLabels[index].isVisible = visible
            logLabels[index].isVisible = visible
        }
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

            cardView.isDisabled = true

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
