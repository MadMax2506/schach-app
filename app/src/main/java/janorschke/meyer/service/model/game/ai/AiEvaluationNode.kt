package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
 */
class AiEvaluationNode(aiColor: PieceColor, val move: Move?, val history: History, val color: PieceColor) {
    val valency: Int

    val requiredMove: Move get() = move!!

    init {
        this.valency = if (move == null) 0
        else {
            // Calculates valency of the current position
            (if (aiColor == PieceColor.WHITE) 1 else -1) * Board(move.fieldsAfterMoving).let { boardCopy ->
                history.push(move)

                val color = move.fromPiece.color.opponent()

                if (BoardValidator.isKingCheckmate(boardCopy, color)) Int.MAX_VALUE
                if (BoardValidator.isStalemate(boardCopy, history, color)) Int.MIN_VALUE
                getPieceValue(boardCopy, PieceColor.WHITE) - getPieceValue(boardCopy, PieceColor.BLACK)
            }
        }
    }

    /**
     * @param board instance
     * @param color of the related pieces
     * @return the valence of all pieces by the given color
     */
    private fun getPieceValue(board: Board, color: PieceColor): Int {
        return PieceSequence
                .allPiecesByColor(board, color)
                .filter { indexedPiece -> indexedPiece.piece !is King }
                .sumOf { indexedPiece -> indexedPiece.piece.pieceInfo.valence }
    }
}