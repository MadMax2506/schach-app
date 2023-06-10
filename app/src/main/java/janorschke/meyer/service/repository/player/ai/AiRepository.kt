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
import janorschke.meyer.service.utils.AiTreeGenerator.generateChildren
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
        root = AiEvaluationNode(root).requiredChildren.first { it.move == move }
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
        var alpha = Int.MIN_VALUE
        var priority = Int.MIN_VALUE
        var nodes: MutableList<AiEvaluationNode> = mutableListOf()

        val time = measureTimeMillis {
            // FIXME
            Log.d(LOG_TAG, "depth=0/$depth | children=${root.children?.toMutableList()?.size}")
            
            if (root.children == null) {
                val board: Board
                val color: PieceColor

                if (root.move == null) {
                    board = Board()
                    color = PieceColor.WHITE
                } else {
                    board = Board(root.requiredMove.fieldsAfterMoving)
                    color = root.color.opponent()
                }
                root.requiredChildren = generateChildren(root, board, color, aiColor)
            }

            for (child in root.requiredChildren) {
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

        Log.d(LOG_TAG, "Calculate the tree in ${time}ms")
        return nodes
    }

    /**
     * @param parent [AiEvaluationNode]
     * @param currentDepth of the [AiRepository.root]
     * @param alpha value to optimize the [AiRepository.minimax]-algorithm
     * @param beta value to optimize the [AiRepository.minimax]-algorithm
     * @param color of the pieces for the current depth
     * @return best [AiEvaluationNode.valency] of the evaluated children
     */
    private fun minimax(parent: AiEvaluationNode, currentDepth: Int, alpha: Int, beta: Int, color: PieceColor): Int {
        if (currentDepth == depth) return parent.valency

        val board = Board(parent.requiredMove.fieldsAfterMoving)
        val maximizingPlayer = (color == aiColor)

        var bestVal = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        var mutableAlpha = alpha
        var mutableBeta = beta

        // FIXME
        Log.d(LOG_TAG, "depth=$currentDepth/$depth | children=${parent.children?.toMutableList()?.size}")

        if (parent.children == null) {
            parent.requiredChildren = generateChildren(parent, board, color, aiColor)
        }

        for (child in parent.requiredChildren) {
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