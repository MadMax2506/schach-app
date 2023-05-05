package janorschke.meyer.game

import androidx.lifecycle.ViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.piece.Piece
import janorschke.meyer.game.piece.PiecePosition

class GameViewModel : ViewModel() {
    private val board: Board = Board()
    private val boardHistory: BoardHistory = BoardHistory()

    fun getField(position: PiecePosition): Piece? {
        return board.getField(position)
    }

    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     */
    fun movePiece(from: PiecePosition, to: PiecePosition) {
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/23
        boardHistory.push(board.createBoardMove(from, to))
    }

    /**
     * Abstract on click handler for an game field
     *
     * @param position which is selected
     */
    fun onFieldClicked(position: PiecePosition) {
        TODO("Validierung, ob ein eigenes Piece angeklickt => in einen State behalten und beim nächsten klick schauen, ob der Move valide ist")
    }
}
