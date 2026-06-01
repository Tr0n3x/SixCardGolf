package gui

import service.Refreshable
import tools.aqua.bgw.core.BoardGameApplication
import service.RootService

/**
 * Represents the main application for the SixCardGolfGame board game.
 * The application initializes the [RootService] and displays the scenes.
 */
class SixCardGolfGameApplication : BoardGameApplication(
    windowTitle = "SixCardGolfGame"), Refreshable {

    /**
     * The root service instance. This is used to call service methods and access the entity layer.
     */
    val rootService: RootService = RootService()

    /**
     * The main game scene displayed in the application.
     */
    private val gameScene = GameScene(rootService, this)
    private val mainMenuScene = MainMenuScene(rootService, this)
    private val scoresScene = ScoresScene()
    private val optionsMenuScene = OptionsMenuScene(rootService, this)

    /**
     * Initializes the application by displaying the [GameScene].
     */
    init {
        showGameScene(gameScene)
        showMenuScene(mainMenuScene)

        rootService.addRefreshables(
            this,
            gameScene,
            mainMenuScene,
            scoresScene,
            optionsMenuScene,
        )
    }

    /**
     * Refresh after the game started.
     * Hides the Menu Scene.
     */
    override fun refreshAfterGameStart() {
        this.hideMenuScene()
    }

    /** Shows the MainMenuScene. */
    fun showMainMenu() {
        this.showMenuScene(mainMenuScene)
    }

    /** Shows the OptionMenuScene. */
    fun showOptionsMenu() {
        optionsMenuScene.updateReturnButton()
        this.showMenuScene(optionsMenuScene)
    }

    /** Hides the OptionMenuScene. */
    fun hideOptionsMenu() {
        this.hideMenuScene()
    }
}
