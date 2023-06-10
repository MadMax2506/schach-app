package janorschke.meyer.service.model.game.ai

import android.util.Log
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
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
    var children: Sequence<AiEvaluationNode>? = null

    val requiredMove get() = move!!
    var requiredChildren: Sequence<AiEvaluationNode>
        get() = children!!
        set(value) {
            this.children = value
        }
    val color get() = requiredMove.from.requiredPiece.color

    init {
        val time = measureTimeMillis {
            this.valency = if (move == null) {
                // Neutral starting position on the board
                0
            } else {
                // Calculates valency of the current position
                Board(move.fieldsAfterMoving).let { boardCopy ->
                    history.push(move)

                    if (BoardValidator.isKingCheckmate(boardCopy, history, color.opponent())) return@let Int.MAX_VALUE
                    return@let calculatePieceValency(boardCopy, aiColor) - calculatePieceValency(boardCopy, aiColor.opponent())
                }
            }
        }
        Log.d(LOG_TAG, "Calculate valency in ${time}ms of")
    }
}