package gui

import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.CheckBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import kotlin.system.exitProcess

/** The scene for the main menu of the game */
class MainMenuScene(private val rootService: RootService) : MenuScene(
    width = 1920 / 3,
    height = 1080 * 0.67,
    background = ColorVisual(255, 255, 255)
),
    Refreshable {

    // Title of main menu scene
    private val titleLabel = Label(
        text = "Six Card Golf Game",
        width = (1920 / 3),
        height = (1080 * 0.67) - 650,
        posX = 0,
        posY = 30,
        font = Font(30, Color.DARK_GRAY, "JetBrains Mono ExtraBold")
    )

    // Text fields for player names
    private val playerOneTextField = TextField(
        height = 50,
        width = (1920 / 3) - 100,
        posX = 50,
        posY = 100,
        font = Font(20.0, Color(0x0f141f)),
        prompt = "Enter Name..."
    )

    private val playerTwoTextField = TextField(
        height = 50,
        width = (1920 / 3) - 100,
        posX = 50,
        posY = 160,
        font = Font(20.0, Color(0x0f141f)),
        prompt = "Enter Name.."
    )

    private val playerThreeTextField = TextField(
        height = 50,
        width = (1920 / 3) - 100,
        posX = 50,
        posY = 220,
        font = Font(20.0, Color(0x0f141f)),
        prompt = "Enter Name.."
    )

    private val playerFourTextField = TextField(
        height = 50,
        width = (1920 / 3) - 100,
        posX = 50,
        posY = 280,
        font = Font(20.0, Color(0x0f141f)),
        prompt = "Enter Name.."
    )

    /**
     * Label that turns green when player range is between 2 and 4. Otherwise, stays red.
     * @see updateEnoughPlayersLabel
     */
    private val enoughPlayerLabel = Label(
        text = "2 - 4 players required. ✗",
        height = 50,
        width = 200,
        posX = 50,
        posY = 320,
        alignment = Alignment.CENTER_LEFT,
        font = Font(15, Color.RED, "JetBrains Mono ExtraBold")
    )

    private val uniquePlayerLabel = Label(
        text = "Unique player names. ✗",
        height = 50,
        width = 200,
        posX = 385,
        posY = 320,
        alignment = Alignment.CENTER_RIGHT,
        font = Font(15, Color.RED, "JetBrains Mono ExtraBold")
    )

    /** Decides if the players will be shuffled or not */
    private val shuffleCheckBox = CheckBox(
        posX = 50,
        posY = 380,
        width = 175,
        height = 50,
        text = "Shuffle players",
        alignment = Alignment.CENTER_LEFT,
        font = Font(20.0, Color.DARK_GRAY, "JetBrains Mono ExtraBold"),
        allowIndeterminate = false
    )

    /**
     * Decides whether the game starts with [service.GameService.createDeck]
     * or [service.GameService.createShortDeck].
     */
    private val testModeCheckBox = CheckBox(
        posX = 50,
        posY = 420,
        width = 175,
        height = 50,
        text = "Test mode",
        alignment = Alignment.CENTER_LEFT,
        font = Font(20.0, Color.DARK_GRAY, "JetBrains Mono ExtraBold"),
        allowIndeterminate = false
    )

    /** Quits the game. */
    private val exitButton = Button(
        posX = 50,
        posY = 630,
        width = 130,
        height = 50,
        text = "Quit",
        font = Font(20.0, Color.DARK_GRAY, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(221, 136, 136))
    ).apply {
        onMouseClicked = {
            exitProcess(0)
        }
    }

    /** Starts the game. */
    private val startButton = Button(
        posX = (1920 / 3) - 180,
        posY = 630,
        width = 130,
        height = 50,
        text = "Start",
        font = Font(20.0, Color.DARK_GRAY, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(136, 221, 136))
    ).apply {
        onMouseClicked = {
            val playerList = listOf(
                playerOneTextField.text.trim(),
                playerTwoTextField.text.trim(),
                playerThreeTextField.text.trim(),
                playerFourTextField.text.trim()
            ).filter { it.isNotBlank() }

            if (playerList.size >= 2) {
                rootService.gameService.startGame(
                    playerNames = playerList,
                    randomOrder = shuffleCheckBox.isChecked,
                    testMode = testModeCheckBox.isChecked
                )
            } else {
                println("At least two player are needed to start the game.")
            }
        }
    }

    init {
        addComponents(titleLabel)
        addComponents(playerOneTextField)
        addComponents(playerTwoTextField)
        addComponents(playerThreeTextField)
        addComponents(playerFourTextField)
        addComponents(enoughPlayerLabel)
        addComponents(uniquePlayerLabel)
        addComponents(shuffleCheckBox)
        addComponents(testModeCheckBox)
        addComponents(exitButton)
        addComponents(startButton)

        // Listener to check if enough player names have been added
        listOf(
            playerOneTextField,
            playerTwoTextField,
            playerThreeTextField,
            playerFourTextField
        ).forEach {
            it.onKeyReleased = {
                updateEnoughPlayersLabel()
                updateUniquePlayersLabel()
            }
        }
    }

    /** Replaces the text of `enoughPlayersLabel` label when enough player names are entered */
    private fun updateEnoughPlayersLabel() {
        val count = listOf(
            playerOneTextField.text,
            playerTwoTextField.text,
            playerThreeTextField.text,
            playerFourTextField.text
        ).count { it.isNotBlank() }

        if (count >= 2) {
            enoughPlayerLabel.text = ("2 - 4 players required. ✓")
            enoughPlayerLabel.font = Font(15, Color.GREEN, "JetBrains Mono ExtraBold")
        } else {
            enoughPlayerLabel.text = ("2 - 4 players required. ✗")
            enoughPlayerLabel.font = Font(15, Color.RED, "JetBrains Mono ExtraBold")
        }
    }

    /** Replaces the text of `enoughUniqueLabel` label when all player names are unique. */
    private fun updateUniquePlayersLabel() {
        val names = listOf(
            playerOneTextField.text,
            playerTwoTextField.text,
            playerThreeTextField.text,
            playerFourTextField.text
        ).filter { it.isNotBlank() }

        val unique = names.map { it.trim().lowercase() }.toSet().size == names.size

        if (unique) {
            uniquePlayerLabel.text = ("Unique player names. ✓")
            uniquePlayerLabel.font = Font(15, Color.GREEN, "JetBrains Mono ExtraBold")
        } else {
            uniquePlayerLabel.text = ("Unique player names. ✗")
            uniquePlayerLabel.font = Font(15, Color.RED, "JetBrains Mono ExtraBold")
        }
    }
}


