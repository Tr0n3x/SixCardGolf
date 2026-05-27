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
    private val gameScene = GameScene(rootService)
    private val mainMenuScene = MainMenuScene(rootService)
    private val scoresScene = ScoresScene()

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
        )
    }

    /**
     * Refresh after the game started.
     * Hides the Menu Scene.
     */
    override fun refreshAfterGameStart() {
        this.hideMenuScene()
    }

    /**
     * Refresh after the game ended and player want to return to main menu.
     * Shows mainMenu and sets currentGame back to null.
     */
    override fun refreshAfterReturnToMenu() {
        showMenuScene(mainMenuScene)
        rootService.currentGame = null
    }
}
