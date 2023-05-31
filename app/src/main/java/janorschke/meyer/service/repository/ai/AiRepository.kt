package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.AiEvaluationNode
import janorschke.meyer.service.model.game.LOG_TAG
import janorschke.meyer.service.model.game.ai.AiEvaluationTreeGenerator
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import kotlin.system.measureTimeMillis

/**
 * Handles the ai moves
 *
 * @param aiColor of the ai
 * @param level of the ai
 */
abstract class AiRepository(private val aiColor: PieceColor, private val level: AiLevel) {
    private val depth get() = level.depth + 1

    init {
        if (level.depth < 0 || level.depth % 2 != 0) throw IllegalArgumentException("Depth is invalid")
    }

    /**
     * @return the next possible move
     */
    fun calculateNextMove(board: Board, history: History) = calculateBestMove(board, history)

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    private fun calculateBestMove(board: Board, history: History): Move {
        var alpha = Int.MIN_VALUE
        var node: AiEvaluationNode? = null

        val time = measureTimeMillis {
            val lastMove = history.getMoves().lastOrNull()
            val root = AiEvaluationNode(aiColor, lastMove, History(history))

            for (child in AiEvaluationTreeGenerator.generateChildren(root, board, aiColor)) {
                val value = minimax(child, 1, alpha, Int.MAX_VALUE, aiColor)

                if (value > alpha) {
                    alpha = value
                    node = child
                }
            }
        }

        Log.d(LOG_TAG, "Calculate the best move in ${time}ms")
        return node!!.requiredMove
    }

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    private fun minimax(parent: AiEvaluationNode, currentDepth: Int, alpha: Int, beta: Int, color: PieceColor): Int {
        if (currentDepth == depth) return parent.valency

        val board = Board(parent.requiredMove.fieldsAfterMoving)
        val maximizingPlayer = color == aiColor

        var bestVal = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        var mutableAlpha = alpha
        var mutableBeta = beta

        for (child in AiEvaluationTreeGenerator.generateChildren(parent, board, color.opponent())) {
            val calcNode = minimax(child, currentDepth + 1, mutableAlpha, mutableBeta, color.opponent())

            if (maximizingPlayer) {
                bestVal = bestVal.coerceAtLeast(calcNode)
                mutableAlpha = mutableAlpha.coerceAtLeast(bestVal)
            } else {
                bestVal = bestVal.coerceAtMost(calcNode)
                mutableBeta = mutableBeta.coerceAtMost(bestVal)
            }

            if (mutableBeta <= mutableAlpha) break
        }
        return bestVal
    }
}