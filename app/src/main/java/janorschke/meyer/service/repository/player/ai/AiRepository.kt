package janorschke.meyer.service.repository.player.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.repository.player.PlayerRepository
import janorschke.meyer.service.utils.ai.AiEvaluation.minimax
import janorschke.meyer.service.utils.ai.AiTreeGenerator.generateChildren
import kotlin.system.measureTimeMillis

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 *
 * @param aiColor
 * @param level of the ai
 */
abstract class AiRepository(private val aiColor: PieceColor, private val level: AiLevel) : PlayerRepository {
    private val depth get() = level.depth + 1

    init {
        if (level.depth < 0 || level.depth % 2 != 0) throw IllegalArgumentException("Depth is invalid")
    }

    override fun nextMove(board: Board, history: History) = calculateBestMove(board, history).random().requiredMove

    /**
     * Calculates the best [Move] of the ai
     * @return the best [Move] for the current ai
     */
    private fun calculateBestMove(board: Board, history: History): MutableList<AiEvaluationNode> {
        val lastMove = history.getMoves().lastOrNull()
        val root = AiEvaluationNode(history, lastMove, aiColor, 0)

        var alpha = Int.MIN_VALUE
        var priority = Int.MIN_VALUE
        var nodes: MutableList<AiEvaluationNode> = mutableListOf()

        val time = measureTimeMillis {
            for (child in generateChildren(root, board, aiColor, aiColor)) {
                val calcVal = minimax(
                        parent = child,
                        depth = depth,
                        currentDepth = 1,
                        alpha = alpha,
                        beta = Int.MAX_VALUE,
                        color = aiColor.opponent(),
                        aiColor = aiColor
                )
                val calcPriority = child.priority

                when {
                    // Node has a better valency
                    alpha < calcVal -> {
                        alpha = calcVal
                        priority = calcPriority
                        nodes = mutableListOf(child)
                    }

                    // Node has the same valency and priority
                    alpha == calcVal && priority == calcPriority -> nodes.add(child)

                    // Node has the same valency and a better priority
                    alpha == calcVal && priority < calcPriority -> {
                        priority = calcPriority
                        nodes = mutableListOf(child)
                    }
                }
            }
        }

        Log.d(LOG_TAG, "Calculate the tree in ${time}ms")
        return nodes
    }
}