package gui

import entity.Player
import service.Refreshable
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.visual.ColorVisual

/**
 * Class for scoreboard.
 *
 * Even though the actual scoreboard is built in [GameScene], so that the background is still visible.
 */
class ScoresScene: MenuScene(
    width = 600,
    height = 600,
    background = ColorVisual(108, 168, 59)
), Refreshable {

    /**
     * Sorts players for scoreboard.
     *
     * @param players The list of players.
     * @return sorted players
     * */
    fun getSortedPlayers(players: List<Player>): List<Player> {
        return players.sortedBy { it.score }
    }
}