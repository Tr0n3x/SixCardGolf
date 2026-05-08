package service

import entity.Player
import entity.SixCardGolfGame

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {
    var final = false
/**
    private fun finishMove() {
        val game = rootService.currentGame
        val currentPlayer = game!!.currentPlayer
        var playsRemaining = game.players.size
        if(!final) {
            if(allCardsOpen(game.players[game.currentPlayer])) {
                final = true
                playsRemaining -= playsRemaining
            }
        } else {
            if (allCardsOpen(game.players[game.currentPlayer])) {
                playsRemaining -= playsRemaining
            }
        }
        if (playsRemaining == 0) {
            endGame()
        } else {
            rootService.gameService.nextPlayer()
        }
    }
    private fun endGame() {
        val game = rootService.currentGame
        for(player in game?.players!!) {
                for (card in player.slots) {
                    if(card != null && card.faceDown) {
                        card.flip()
                    }
                }
            }
            for(player in game.players) { // Zeile 39/42 in removeRow rein
                if(player.slots[0]?.value == player.slots[2]?.value && player.slots[0]?.value == player.slots[4]?.value) {
                    removeRow(0)
                }
                if(player.slots[1]?.value == player.slots[3]?.value && player.slots[1]?.value == player.slots[5]?.value) {
                    removeRow(1)
                }
            }
            for(player in game.players) {
                calculateScore(player)
            }
    }

    private fun removeRow(row: Int) {
        val game = rootService.currentGame
        if(row == 0) {
            game?.players[game.currentPlayer]?.slots[0] = null
            game?.players[game.currentPlayer]?.slots[2] = null
            game?.players[game.currentPlayer]?.slots[4] = null
        } else if (row == 1) {
            game?.players[game.currentPlayer]?.slots[1] = null
            game?.players[game.currentPlayer]?.slots[3] = null
            game?.players[game.currentPlayer]?.slots[5] = null
        } else {
            throw IllegalArgumentException("Row has to be 0 or 1.")
        }
    }

    private fun boolRowRemoved(row: Int): Boolean {
        val game = rootService.currentGame
        if(row == 0 && game?.players[game.currentPlayer]?.slots[0] == null && game?.players[game.currentPlayer]?.slots[2] == null && game?.players[game.currentPlayer]?.slots[4] == null) {
            return true
        }
        if(row == 1 && game?.players[game.currentPlayer]?.slots[1] == null && game?.players[game.currentPlayer]?.slots[3] == null && game?.players[game.currentPlayer]?.slots[5] == null) {
            return true
        }
        return false
    }

    private fun boolColumnEqual(column1: Int, column2: Int): Boolean {
        val game = rootService.currentGame
        if(game?.players[game.currentPlayer]?.slots[column1] == game?.players[game.currentPlayer]?.slots[column2]) return true
        return false
    }

    /** TODO */
    private fun calculateScore(player: Player): Int {
        val game = rootService.currentGame
        var score = 0
        if ((boolRowRemoved(0) && boolRowRemoved(1))
            || boolColumnEqual(0,1) && boolColumnEqual(2,3) && boolColumnEqual(4,5)) {
            return 0
        }
        if (boolRowRemoved(0) && !boolRowRemoved(1)) {
            // Add Score Line 2
        }
        if (boolRowRemoved(1) && !boolRowRemoved(0)) {
            // Add Score Line 1
        }
        if (!boolRowRemoved(0) && !boolRowRemoved(1)) {
            if (!boolColumnEqual(0,1)) {
                // Add Score Slot 0, Slot 1
            }
            if (!boolColumnEqual(2,3)) {
                // Add Score Slot 2, Slot 3
            }
            if (!boolColumnEqual(4,5)) {
                // Add Score Slot 4, Slot 5
            }
        }
        return score
    }

    fun allCardsOpen(player: Player): Boolean {
        return player.slots.all { it?.faceDown == false}
    }
    */
}
