package janorschke.meyer.service.model

import janorschke.meyer.service.utils.board.PiecePosition

object SelectedPiece {
    /**
     * Current selected position
     */
    private var selectedPosition: PiecePosition? = null

    /**
     * Possible moves for the piece on the selected position
     */
    private var possibleMoves: MutableList<PiecePosition> = mutableListOf()

    fun reset() {
        selectedPosition = null
        possibleMoves.clear()
    }


    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     *
     * @param position the position of the selected piece
     * @param possibleMoves the possible moves for the selected piece
     */
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PiecePosition> = mutableListOf()) {
        this.selectedPosition = selectedPosition
        this.possibleMoves = possibleMoves
    }

    fun getSelectedPosition() = selectedPosition

    fun getPossibleMoves() = possibleMoves
}