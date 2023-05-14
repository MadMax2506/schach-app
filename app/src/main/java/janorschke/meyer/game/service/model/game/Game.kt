package janorschke.meyer.game.service.model.game

import janorschke.meyer.game.service.utils.board.PiecePosition

object Game {
    private var selectedPosition: PiecePosition? = null

    private lateinit var status: GameStatus
    private lateinit var possibleMoves: MutableList<PiecePosition>

    init {
        reset()
    }

    fun reset() {
        status = GameStatus.RUNNING
        selectedPosition = null

        possibleMoves.clear()
    }

    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     *
     * @param position the position of the selected piece (optional: Default = null)
     * @param possibleMoves the possible moves for the selected piece (optional: Default = emptyList())
     */
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PiecePosition> = mutableListOf()) {
        this.selectedPosition = selectedPosition
        this.possibleMoves = possibleMoves
    }

    /**
     * Sets status of the game
     *
     * @param status of the game
     */
    fun setStatus(status: GameStatus) {
        this.status = status
    }

    fun getSelectedPosition() = selectedPosition

    fun getStatus() = status

    fun getPossibleMoves() = possibleMoves
}