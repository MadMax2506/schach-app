package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move

/**
 * TODO
 */
class AiEvaluationTree(
        // TODO
        private val deepness: Int,
        private val board: Board,
        // TODO
        private val aiColor: PieceColor
) {
    private val aiEvaluationTreeGenerator = AiEvaluationTreeGenerator(deepness)
    private var root = aiEvaluationTreeGenerator.generate(AiEvaluationNode(aiColor), board)

    fun getRoot() = root

    /**
     * TODO
     */
    fun applyMove(move: Move) {
        root.requiredChildren()
                .first { node -> node.requiredMove == move }
                .let { node -> root = aiEvaluationTreeGenerator.generate(node, board) }
    }

    /**
     * TODO
     */
    fun calculateBestMove(): Move {
        var alpha = Int.MIN_VALUE
        var node: AiEvaluationNode? = null

        for (child in root.requiredChildren()) {
            val value = min(child, alpha, Int.MAX_VALUE)

            if (value > alpha) {
                alpha = value;
                node = child;
            }
        }
        return node!!.requiredMove
    }

    /**
     * TODO
     */
    private fun max(parent: AiEvaluationNode, alpha: Int, beta: Int): Int {
        if (parent.numberOfChildren == 0) return parent.valency

        var bestVal = Int.MIN_VALUE
        var mutableAlpha = alpha
        for (child in parent.requiredChildren()) {
            val calcNode = min(child, mutableAlpha, beta)

            bestVal = bestVal.coerceAtLeast(calcNode)
            mutableAlpha = mutableAlpha.coerceAtLeast(bestVal)

            if (beta <= mutableAlpha) break
        }
        return bestVal
    }

    /**
     * TODO
     */
    private fun min(parent: AiEvaluationNode, alpha: Int, beta: Int): Int {
        if (parent.numberOfChildren == 0) return parent.valency

        var bestVal = Int.MAX_VALUE
        var mutableBeta = beta
        for (child in parent.requiredChildren()) {
            val calcNode = max(child, alpha, mutableBeta)

            bestVal = bestVal.coerceAtMost(calcNode)
            mutableBeta = mutableBeta.coerceAtMost(bestVal)

            if (mutableBeta <= alpha) break
        }
        return bestVal
    }
}