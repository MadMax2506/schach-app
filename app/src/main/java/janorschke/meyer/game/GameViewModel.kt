package janorschke.meyer.game

import androidx.lifecycle.ViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.player.PlayerInfo

class GameViewModel : ViewModel() {
    private val board: Board = Board()
    private val boardHistory: BoardHistory = BoardHistory()
    private var selectedPiecePosition: PiecePosition? = null
    lateinit var playerInfo: PlayerInfo

    fun getField(position: PiecePosition): Piece? {
        return board.getField(position)
    }

    /**
     * Moves a piece to another position
     *
     * @param from source position
     * @param to target position
     */
    fun movePiece(from: PiecePosition, to: PiecePosition) {
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/23
        boardHistory.push(board.createBoardMove(from, to))
    }

    /**
     * OnClick handler for a game field
     *
     * @param position which is selected
     */
    fun onFieldClicked(position: PiecePosition) {
        val piece = board.getField(position) ?: return

        if (selectedPiecePosition == null && piece.color == playerInfo.color) {
            selectedPiecePosition = position
            // show possibleMoves
        } else if (selectedPiecePosition != null && position != selectedPiecePosition) {
            // dont show moves from selectedPosition,
            // validate if move is ok and move,
            // if click on another white piece, set new selectedposition
        } else {
            // dont show possibleMoves anymore
        }

    }
}
