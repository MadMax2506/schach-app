package janorschke.meyer.service.model.game.ai

import android.util.Log
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.BoardUtils.calculatePieceValency
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

                    val valueAi = calculatePieceValency(boardCopy, aiColor)
                    val valueDiff = valueAi - calculatePieceValency(boardCopy, aiColor.opponent())

                    if (BoardValidator.isKingCheckmate(boardCopy, history, color.opponent())) return@let Int.MAX_VALUE
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
}