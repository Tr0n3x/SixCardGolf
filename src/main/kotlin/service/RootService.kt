package service

import entity.SixCardGolfGame
import gui.CardDesign

/**
 * The root service class is responsible for managing services and the entity layer reference.
 * This class acts as a central hub for every other service within the application.
 */
class RootService {

    /** Sets the initial card design */
    var cardDesign: CardDesign = CardDesign.CLASSIC

    /** The connected [GameService] for this rootService */
    val gameService = GameService(this)

    /** The connected [PlayerActionService] for this rootService */
    val playerActionService = PlayerActionService(this)

    /**
     * The currently active [entity.SixCardGolfGame]. Can be `null`, if no game has started yet.
     */
    var currentGame: SixCardGolfGame? = null

    /**
     * Adds the provided [newRefreshable] to all services connected to this root service
     *
     * @param newRefreshable The [Refreshable] to be added
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     *
     * @param newRefreshables The [Refreshable]s to be added
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }
}
