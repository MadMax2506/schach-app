package janorschke.meyer.game.board.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceSequence

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
            val whiteMoves = last10moves.filter { it.fromPiece.color == PieceColor.WHITE }
            val firstWhiteMove = whiteMoves[0]
            val secondWhiteMove = whiteMoves[1]
            whiteMoves.forEachIndexed { i, move ->
                if (i % 2 == 0) {
                    if(firstWhiteMove != move) return false
                } else {
                    if(secondWhiteMove != move) return false
                }
            }

            val blackMoves = last10moves.filter { it.fromPiece.color == PieceColor.BLACK }
            val firstBlackMove = blackMoves[0]
            val secondBlackMove = blackMoves[1]
            blackMoves.forEachIndexed { i, move ->
                if (i % 2 == 0) {
                    if(firstBlackMove != move) return false
                } else {
                    if(secondBlackMove != move) return false
                }
            }
            return true
        }
    }
}