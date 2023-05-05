package janorschke.meyer.game.board

class BoardHistory {
    private val history: ArrayDeque<BoardMove> = ArrayDeque(listOf())

    /**
     * Add a new move to the history
     *
     * @param move of a piece
     */
    fun push(move: BoardMove) {
        history.add(move)
    }

    /**
     * @return the last move and remove it from the history
     */
    fun undo(): BoardMove {
        return history.removeLast()
    }

    /**
     * Resets the board to the initial state
     */
    fun reset() {
        history.clear()
    }
}