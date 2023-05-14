package janorschke.meyer.view.listener

import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.board.BoardCopy
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.service.utils.board.PiecePosition
import janorschke.meyer.viewModel.BoardViewModel
import janorschke.meyer.viewModel.SelectedPieceViewModel

/**
 * Handles the click on a game field
 */
class GameFieldOnClickListener(
        private val position: PiecePosition,
        private val board: Array<Array<Piece?>>,
        private val playerColor: PieceColor,
        private val selectedPosition: PiecePosition?,
        private val selectedPieceViewModel: SelectedPieceViewModel,
        private val boardViewModel: BoardViewModel
) : OnClickListener {
    override fun onClick(v: View?) {
        val piece = board[position.row][position.col]
        val possibleMoves = piece?.possibleMoves(BoardCopy(board), position) ?: mutableListOf()

        val isPlayersPiece = (piece?.color == playerColor)

        Log.d("", "HELLO WORLD :-)")
        when {
            // handle first click
            (selectedPosition == null && isPlayersPiece) -> selectedPieceViewModel.setSelectedPiece(position, possibleMoves)
            // handle second click
            (selectedPosition != null && !isPlayersPiece) -> boardViewModel.tryToMovePiece(selectedPosition!!, position)
            (isPlayersPiece && selectedPosition != position) -> selectedPieceViewModel.setSelectedPiece(position, possibleMoves)
            else -> selectedPieceViewModel.setSelectedPiece()
        }
    }
}