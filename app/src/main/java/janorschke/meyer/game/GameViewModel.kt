package janorschke.meyer.game

import androidx.lifecycle.ViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.Piece
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
        val piece = board.getField(position)

        // handle first click
        if (selectedPiecePosition == null && piece != null && piece.color == playerInfo.color) {
            selectedPiecePosition = position
            val possibleMoves = piece.possibleMoves(position)
            // show possibleMoves
        } else { // handle second click
            if (piece != null && piece.color == playerInfo.color) {
                // clicking on own piece
            } else {
                // check if move is valid
                // execute move
                // don't show possibleMoves anymore
            }

        }
    }
}
