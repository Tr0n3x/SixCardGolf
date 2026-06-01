package gui

import entity.CardSuit
import entity.CardValue
import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

class OptionsMenuScene(private val rootService: RootService, private val application: SixCardGolfGameApplication) :
    MenuScene(
        width = 1920 / 3,
        height = 1080 * 0.67,
        background = ColorVisual(255, 255, 255)
    ), Refreshable {

    // MainMenu Button
    private val mainMenuButton = Button(
        posX = 50,
        posY = 630,
        width = 130,
        height = 50,
        text = "Main Menu",
        font = Font(20.0, Color(0x0f141f)),
        visual = ColorVisual(Color(136, 221, 136))
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.currentGame = null
            rootService.gameService.onAllRefreshables { refreshAfterCardDesignChange() }
            application.showMainMenu()
        }
    }

    private val returnToGameButton = Button(
        posX = (1920 / 3) - 180,
        posY = 630,
        width = 130,
        height = 50,
        text = "Back to Game",
        font = Font(20.0, Color(0x0f141f)),
        visual = ColorVisual(Color(136, 221, 136))
    ).apply {
        isVisible = true
        onMouseClicked = {
            application.hideOptionsMenu()
            rootService.gameService.onAllRefreshables { refreshAfterCardDesignChange() }
        }
    }

    private val warningLabel = Label(
        posX = 50,
        posY = 680,
        width = 300,
        height = 25,
        text = "This ends the running game!",
        font = Font(20.0, Color(255, 125, 125)),
        alignment = Alignment.CENTER_LEFT,
    ).apply {
        isVisible = false
    }

    // Card Design
    private var currentDesign = CardDesign.CLASSIC

    private val designTitleLabel = Label(
        posX = 220,
        posY = 20,
        width = 200,
        height = 50,
        text = "Deck design",
        font = Font(30.0, Color.DARK_GRAY),
        alignment = Alignment.CENTER,
    )

    private val designLabel = Label(
        posX = 220,
        posY = 100,
        width = 200,
        height = 40,
        text = currentDesign.name,
        font = Font(20.0, Color.DARK_GRAY),
        alignment = Alignment.CENTER
    )

    private val leftButton = Button(
        posX = 190,
        posY = 100,
        width = 40,
        height = 40,
        text = "<"
    ).apply {
        onMouseClicked = {
            currentDesign = currentDesign.previous()
            rootService.cardDesign = currentDesign
            designLabel.text = currentDesign.name
            updateCardPreview()
        }
    }

    private val rightButton = Button(
        posX = 410,
        posY = 100,
        width = 40,
        height = 40,
        text = ">"
    ).apply {
        onMouseClicked = {
            currentDesign = currentDesign.next()
            rootService.cardDesign = currentDesign
            designLabel.text = currentDesign.name
            updateCardPreview()
        }
    }

    // Preview Cards
    private val previewCardFront = Label(
        posX = 270,
        posY = 180,
        width = 130/3,
        height = 200/3,
    )

    private val previewCardBack = Label(
        posX = 325,
        posY = 180,
        width = 130/3,
        height = 200/3,
    )

    init {
        addComponents(mainMenuButton, returnToGameButton, warningLabel)
        addComponents(designTitleLabel, leftButton, rightButton, designLabel)
        addComponents(previewCardFront, previewCardBack)

        // Listener for mainMenuButton
        if (rootService.currentGame != null) {
            mainMenuButton.onMouseEntered = {
                warningLabel.isVisible = true
            }
            mainMenuButton.onMouseExited = {
                warningLabel.isVisible = false
            }
        }

        updateCardPreview()
    }
    fun updateReturnButton() {
        returnToGameButton.isDisabled = rootService.currentGame == null

        returnToGameButton.visual =
            if (returnToGameButton.isDisabled)
                ColorVisual(Color(128,128,128))
            else
                ColorVisual(Color(136,221,136))
    }

    private fun updateCardPreview() {
        val cardLoader = CardImageLoader(rootService.cardDesign)

        previewCardFront.visual = cardLoader.frontImageFor(CardSuit.HEARTS, CardValue.THREE)
        previewCardBack.visual = cardLoader.backImage
    }
}