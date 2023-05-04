package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

abstract class Piece(val boardViewModel: BoardViewModel, val color: PieceColor) {
    /**
     * @param position being checked
     * @return true, if a piece can move to the position
     */
    protected fun isFieldAvailable(position: PiecePosition): Boolean {
        val board = boardViewModel.getBoard()

        val indices = board.indices
        if (position.row !in indices || position.col !in indices) return false

        val piece = board[position.row][position.col]
        return if (piece == null) true else piece.color != this.color
    }

    /**
     * @return the image id for the piece
     */
    abstract fun getImageId(): Int

    /**
     * @param position current position
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition>

}