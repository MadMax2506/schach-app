package janorschke.meyer.service.validator

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.utils.piece.PieceSequence

object BoardValidator {
    private const val N_MOVE_REPETITIONS_FOR_STALEMATE = 10

    /**
     * @param board instance
     * @param color of the king that can be in check
     *
     * @return true, if King is in check
     */
    fun isKingInCheck(board: Board, color: PieceColor): Boolean {
        val kingPosition = board.findKingPosition(color) ?: return true

        return PieceSequence.allPiecesByColor(board, color.opponent())
                .any { it.piece.givesOpponentKingCheck(board, it.position, kingPosition) }
    }

    /**
     * @param board instance
     * @param color of the king that can be in checkmate
     *
     * @return true, if the king of the given color is in checkmate
     */
    fun isKingCheckmate(board: Board, color: PieceColor): Boolean {
        if (!isKingInCheck(board, color)) return false

        // check if any piece can go somewhere, that is not checkmate
        PieceSequence.allPiecesByColor(board, color)
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
     * @param board instance
     * @param history to check the move-repetition
     * @param color of the player who has the next turn
     *
     * @return true, if the game with the rest pieces is stalemate
     */
    fun isStalemate(board: Board, history: History, color: PieceColor): Boolean {
        if (isKingInCheck(board, color)) return false

        val pieceSequence = PieceSequence.allPiecesByColor(board, color)
        val pieceSequenceOpponent = PieceSequence.allPiecesByColor(board, color.opponent())

        // check if no possible move for the given color is left
        pieceSequence.map { it.piece.possibleMoves(board, it.position) }
                .flatten()
                .toList()
                .isEmpty()
                .apply { if (this) return true }

        // check if both player has enough pieces
        if (!checkIfPlayerCanWin(pieceSequence) && !checkIfPlayerCanWin(pieceSequenceOpponent)) return true

        // check move-repetition
        if (N_MOVE_REPETITIONS_FOR_STALEMATE >= history.numberOfMoves()) return false
        history.getLastMoves(N_MOVE_REPETITIONS_FOR_STALEMATE).apply {
            return hasColorRepeatedMoves(this, PieceColor.WHITE) && hasColorRepeatedMoves(this, PieceColor.BLACK)
        }
    }

    /**
     * Checks if the remaining pieces of a colors can win the game
     *
     * @param pieceSequence contains all pieces of an color
     */
    private fun checkIfPlayerCanWin(pieceSequence: Sequence<PieceSequence.IndexedPiece>): Boolean {
        pieceSequence.map { it.piece }
                .filterNot { it is King }
                .toList()
                .apply {
                    if (this.isEmpty()) return false
                    if (this.size == 1 && this[0].pieceInfo.valence == 3) return false
                }
        return true
    }

    /**
     * @param moveHistory to check the move-repetition for the given color
     * @param color TODO
     */
    private fun hasColorRepeatedMoves(moveHistory: List<Move>, color: PieceColor): Boolean {
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