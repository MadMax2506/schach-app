package janorschke.meyer.game.board.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.utils.PieceSequence

abstract class BoardValidator {
    companion object {
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

        fun isStalemate(board: Board, opponent: PieceColor): Boolean {
            // TODO
            return false
        }
    }
}