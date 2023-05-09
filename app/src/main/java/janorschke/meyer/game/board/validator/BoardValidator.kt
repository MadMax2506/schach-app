package janorschke.meyer.game.board.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.King

abstract class BoardValidator {
    companion object {
        /**
         * Searches for the King on the board with the given color
         * @param color of the piece
         * @return position of the King
         */
        fun findKingPosition(board: Board, color: PieceColor): PiecePosition {
            return board.getFields().flatten()
                    .filter { it is King && it.color == color }
                    .withIndex()
                    .map { PiecePosition(it.index) }
                    .firstOrNull()
                    ?: throw IllegalStateException("King with color $color could not be found!")
        }

        /**
         * @return true, if King is in check
         */
        fun isKingInCheck(board: Board, color: PieceColor): Boolean {
            return board.getFields().flatten()
                    .filterNotNull()
                    .withIndex()
                    .filter { it.value.color == color.opponent() }
                    // wird in possibleMoves aufgerufen => rekursion zwischen 2 Funktionen
                    .filter { it.value.possibleMoves(PiecePosition(it.index), true).contains(this.findKingPosition(board, color)) }
                    .toList()
                    .isNotEmpty()
        }

        /**
         * @return true if the King of the given color is in checkmate, false otherwise
         */
        fun isKingCheckmate(board: Board, color: PieceColor): Boolean {
            if (!isKingInCheck(board, color)) return false

            // check if any piece can go somewhere, that is not checkmate
            return board.getFields().flatten()
                    .filterNotNull()
                    .withIndex()
                    .none { piece ->
                        if (piece.value.color == color) {
                            val moves = piece.value.possibleMoves(PiecePosition(piece.index))
                            moves.forEach { move ->
                                val boardCopy = board.getFields().map { it.copyOf() }.toTypedArray()
                                val movePiece = boardCopy[piece.index / Board.LINE_SIZE][piece.index % Board.LINE_SIZE]
                                boardCopy[piece.index / Board.LINE_SIZE][piece.index % Board.LINE_SIZE] = null
                                boardCopy[move.row][move.col] = movePiece

                                if (!isKingInCheck(board, color)) {
                                    // if King is not in check after this move, then it's not checkmate
                                    return true
                                }
                            }
                        }
                        return false
                    }
        }
    }
}