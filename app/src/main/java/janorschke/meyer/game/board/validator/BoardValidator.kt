package janorschke.meyer.game.board.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.board.BoardMove
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.model.King
import janorschke.meyer.game.piece.utils.PieceSequence

abstract class BoardValidator {
    companion object {
        private const val N_MOVE_REPETIONS_FOR_STALEMATE = 10

        /**
         * @param board current board instance
         * @param color of the king that can be in check
         * @return true, if King is in check
         */
        fun isKingInCheck(board: Board, color: PieceColor): Boolean {
            val kingPosition = board.findKingPosition(color) ?: return true

            return PieceSequence.piecesByColor(board.getFields(), color.opponent())
                    .any { it.piece.givesOpponentKingCheck(board, it.position, kingPosition) }
        }

        /**
         * @param board current board instance
         * @param color of the king that can be in checkmate
         * @return true if the King of the given color is in checkmate, false otherwise
         */
        fun isKingCheckmate(board: Board, color: PieceColor): Boolean {
            if (!isKingInCheck(board, color)) return false

            // check if any piece can go somewhere, that is not checkmate
            PieceSequence.piecesByColor(board.getFields(), color)
                    .forEach {
                        it.piece.possibleMoves(board, it.position).forEach { move ->
                            Board(board).apply {
                                this.createBoardMove(it.position, move)
                                // if King is not in check after this move, then it's not checkmate
                                if (!isKingInCheck(this, color)) return false
                            }
                        }
                    }
            return true
        }

        /**
         * @param board current board instance
         * @param color of the
         */
        fun isStalemate(board: Board, boardHistory: BoardHistory, color: PieceColor): Boolean {
            val pieceSeqByColor = PieceSequence.piecesByColor(board.getFields(), color)

            // check if no possible move for the given color is left
            pieceSeqByColor.map { it.piece.possibleMoves(board, it.position) }
                    .flatten()
                    .toList()
                    .isEmpty()
                    .apply { if (this) return true }

            // check if not enough pieces
            pieceSeqByColor.map { it.piece }
                    .filterNot { it is King }
                    .toList()
                    .apply {
                        if (this.isEmpty()) return true
                        if (this.size == 1 && this.none { it.pieceInfo.valence == 3 }) return true
                    }

            // check move-repetition
            if (N_MOVE_REPETIONS_FOR_STALEMATE >= boardHistory.numberOfMoves()) {
                return false
            }

            val last10moves = boardHistory.getLastMoves(N_MOVE_REPETIONS_FOR_STALEMATE)

            val whiteRepetition = hasColorRepeatedMoves(last10moves, PieceColor.WHITE)
            val blackRepetition = hasColorRepeatedMoves(last10moves, PieceColor.BLACK)

            return whiteRepetition && blackRepetition
        }

        private fun hasColorRepeatedMoves(moveHistory: List<BoardMove>, color: PieceColor): Boolean {
            return moveHistory.filter { it.fromPiece.color == color }
                    .withIndex()
                    .all {
                        if (it.index % 2 == 0) {
                            if (moveHistory[0] != it.value) return false
                        } else {
                            if (moveHistory[1] != it.value) return false
                        }
                        return true
                    }
        }
    }
}