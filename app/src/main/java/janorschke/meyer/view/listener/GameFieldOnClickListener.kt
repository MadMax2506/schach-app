package janorschke.meyer.view.listener

import android.view.View
import android.view.View.OnClickListener
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.viewModel.GameViewModel

/**
 * Handles the click on a game field
 */
class GameFieldOnClickListener(
        private val board: Array<Array<Piece?>>,
        private val position: PiecePosition,
        private val playerColor: PieceColor,
        private val selectedPosition: PiecePosition?,
        private val gameViewModel: GameViewModel
) : OnClickListener {
    override fun onClick(v: View?) {
        val piece = board[position.row][position.col]
        val possibleMoves = piece?.possibleMoves(Board(board), gameViewModel.getHistory(), position) ?: mutableListOf()

        val isPlayersPiece = (piece?.color == playerColor)

        when {
            // handle first click
            (selectedPosition == null && isPlayersPiece) -> gameViewModel.setSelectedPiece(position, possibleMoves)
            // handle second click
            (selectedPosition != null && !isPlayersPiece) -> gameViewModel.tryToMovePiece(selectedPosition, position)
            (isPlayersPiece && selectedPosition != position) -> gameViewModel.setSelectedPiece(position, possibleMoves)
            else -> gameViewModel.setSelectedPiece()
        }
    }
}