package janorschke.meyer.service.model.game.ai

import android.util.Log
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator
import kotlin.system.measureTimeMillis

const val LOG_TAG = "AiEvaluationNode"

/**
 * @param history instance
 * @param move of the current evaluation step
 * @param aiColor
 * @param priority in the evaluation cycle
 */
class AiEvaluationNode(val history: History, val move: Move?, private val aiColor: PieceColor, val priority: Int) {
    val valency: Int

    val requiredMove get() = move!!
    val color get() = requiredMove.fromPiece.color

    init {
        val time = measureTimeMillis {
            this.valency = if (move == null) {
                // Neutral starting position on the board
                0
            } else {
                // Calculates valency of the current position
                Board(move.fieldsAfterMoving).let { boardCopy ->
                    history.push(move)

                    val valueAi = getPieceValue(boardCopy, aiColor)
                    val valueDiff = valueAi - getPieceValue(boardCopy, aiColor.opponent())
                    
                    if (BoardValidator.isKingCheckmate(boardCopy, history, color.opponent())) return@let Int.MAX_VALUE
                    if (BoardValidator.isKingInCheck(boardCopy, history, color.opponent())) return@let Int.MAX_VALUE - 1
                    if (BoardValidator.isStalemate(boardCopy, history, color.opponent())) {
                        // Try to achieve an stalemate if the opponent has a big advantage
                        if (valueAi <= PieceInfo.PAWN.valence && valueDiff < 0) return@let Int.MAX_VALUE
                        if (valueDiff < PieceInfo.QUEEN.valence) return@let Int.MAX_VALUE
                        return@let Int.MIN_VALUE
                    }
                    return@let valueDiff
                }
            }
        }
        Log.d(LOG_TAG, "Calculate valency in ${time}ms of")
    }

    /**
     * @param board instance
     * @param color of the related pieces
     * @return the valence of all pieces by the given color
     */
    private fun getPieceValue(board: Board, color: PieceColor): Int {
        return PieceSequence.allPiecesByColor(board, color)
                .filter { indexedPiece -> indexedPiece.piece !is King }
                .sumOf { indexedPiece -> indexedPiece.piece.pieceInfo.valence }
    }
}