package janorschke.meyer.game.board.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.board.BoardMove
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.utils.PieceSequence

abstract class BoardValidator {
    companion object {
        private const val N_MOVE_REPITIONS_FOR_STALEMATE = 10

        /**
         * @param board current board instance
         * @param color of the king
         * @return true, if King is in check
         */
        fun isKingInCheck(board: Board, color: PieceColor): Boolean {
            return PieceSequence.piecesByColor(board.getFields(), color.opponent())
                    .any { it.piece.givesOpponentKingCheck(board, it.position, board.findKingPosition(color)) }
        }

        /**
         * @param board current board instance
         * @param color of the king
         * @return true if the King of the given color is in checkmate, false otherwise
         */
        fun isKingCheckmate(board: Board, color: PieceColor): Boolean {
            if (!isKingInCheck(board, color)) return false

            // check if any piece can go somewhere, that is not checkmate
            return PieceSequence.piecesByColor(board.getFields(), color)
                    .none {
                        it.piece.possibleMoves(board, it.position).any { move ->
                            Board(board).apply {
                                this.createBoardMove(it.position, move)
                                // if King is not in check after this move, then it's not checkmate
                                if (!isKingInCheck(this, color)) return true
                            }
                            return false
                        }
                    }
        }

        /**
         * @param board current board instance
         * @param color of the
         */
        fun isStalemate(board: Board, boardHistory: BoardHistory, color: PieceColor): Boolean {
            // check if no possible move for the given color is left
//            if () {
//                return true
//            }

            // check if not enough pieces


            // check move-repitition
            if (N_MOVE_REPITIONS_FOR_STALEMATE >= boardHistory.numberOfMoves()) {
                return false
            }

            val last10moves = boardHistory.getLastMoves(N_MOVE_REPITIONS_FOR_STALEMATE)

            val whiteRepitition = hasColorRepeatedMoves(last10moves, PieceColor.WHITE)
            val blackRepitition = hasColorRepeatedMoves(last10moves, PieceColor.BLACK)

            return whiteRepitition && blackRepitition
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