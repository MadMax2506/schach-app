package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.ai.LOG_TAG
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.AiTreeGenerator
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
     * Calculates the best move of the ai
     *
     * @param board instance
     * @param history instance
     * @return the best move for the current ai
     */
    private fun calculateBestMove(board: Board, history: History): Move {
        var alpha = Int.MIN_VALUE
        var priority = Int.MIN_VALUE
        var nodes: MutableList<AiEvaluationNode> = mutableListOf()

        val time = measureTimeMillis {
            val lastMove = history.getMoves().lastOrNull()
            val root = AiEvaluationNode(History(history), lastMove, aiColor, 0)

            for (child in AiTreeGenerator.generateChildren(root, board, aiColor)) {
                val value = minimax(child, 1, alpha, Int.MAX_VALUE, aiColor)

                when {
                    // Node has a better valency
                    alpha < value -> {
                        alpha = value
                        priority = child.priority
                        nodes = mutableListOf(child)
                    }

                    // Node has the same valency and priority
                    alpha == value && priority == child.priority -> nodes.add(child)


                    // Node has the same valency and a better priority
                    alpha == value && priority < child.priority -> {
                        priority = child.priority
                        nodes = mutableListOf(child)
                    }
                }
            }
        }

        Log.d(LOG_TAG, "Calculate the best move in ${time}ms")
        // Choose random the best move with the highest priority
        return nodes.random().requiredMove
    }

    /**
     * @param parent [AiEvaluationNode]
     * @param currentDepth of the tree
     * @param alpha value to optimize the minimax-algorithm
     * @param beta value to optimize the minimax-algorithm
     * @param color of the pieces for the current depth
     * @return best valency of the evaluated children
     */
    private fun minimax(parent: AiEvaluationNode, currentDepth: Int, alpha: Int, beta: Int, color: PieceColor): Int {
        if (currentDepth == depth) return parent.valency

        val board = Board(parent.requiredMove.fieldsAfterMoving)
        val maximizingPlayer = color == aiColor

        var bestVal = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        var mutableAlpha = alpha
        var mutableBeta = beta

        for (child in AiTreeGenerator.generateChildren(parent, board, color.opponent())) {
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