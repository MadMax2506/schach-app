package janorschke.meyer.service.repository.player.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.piece.Piece
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
    private var root: AiEvaluationNode
    private var bestNodes: MutableList<AiEvaluationNode> = mutableListOf()

    private val depth get() = level.depth + 1

    init {
        if (level.depth < 0 || level.depth % 2 != 0) throw IllegalArgumentException("Depth is invalid")

        root = AiEvaluationNode(History(), null, aiColor, 0)
        setBestNodes(generateTreeAndCalculateBestMove())
    }

    override fun apply(move: Move) {
        root = root.requiredChildren.first { it.move == move }
        setBestNodes(generateTreeAndCalculateBestMove(), move.from.requiredPiece.color)
    }

    override fun nextMove() = bestNodes.random().requiredMove

    /**
     * If the next move is by the ai, the `bestNodes` are accepted, otherwise an empty list.
     *
     * @param bestNodes for the next [Move]
     * @param color of the last moving [Piece]
     */
    private fun setBestNodes(bestNodes: MutableList<AiEvaluationNode>, color: PieceColor? = null) {
        this.bestNodes = if (color == null || color != aiColor) bestNodes else mutableListOf()
    }

    /**
     * Calculates the best [Move] of the ai
     * @return the best [Move] for the current ai
     */
    private fun generateTreeAndCalculateBestMove(): MutableList<AiEvaluationNode> {
        var nodes: MutableList<AiEvaluationNode> = mutableListOf()

        val time = measureTimeMillis {
            var alpha = Int.MIN_VALUE
            var priority = Int.MIN_VALUE
            val board: Board
            val color: PieceColor

            if (root.move == null) {
                board = Board()
                color = PieceColor.WHITE
            } else {
                board = Board(root.requiredMove.fieldsAfterMoving)
                color = root.color.opponent()
            }

            if (root.children == null) root.requiredChildren = generateChildren(root, board, color, aiColor)

            for (child in root.requiredChildren) {
                val calcNode = minimax(
                        parent = child,
                        depth = depth,
                        currentDepth = 1,
                        alpha = alpha,
                        beta = Int.MAX_VALUE,
                        color = color.opponent(),
                        aiColor = aiColor
                )
                val calcValency = calcNode.valency
                val calcPriority = child.priority

                when {
                    // Node has a better valency
                    alpha < calcValency -> {
                        alpha = calcValency
                        priority = calcPriority
                        nodes = mutableListOf(child)
                    }

                    // Node has the same valency and priority
                    alpha == calcValency && priority == calcPriority -> nodes.add(child)

                    // Node has the same valency and a better priority
                    alpha == calcValency && priority < calcPriority -> {
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