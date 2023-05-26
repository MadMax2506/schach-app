package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move

/**
 * TODO
 */
class AiEvaluationTree(
        history: History,
        // TODO
        private val level: AiLevel,
        private val board: Board,
        // TODO
        private val aiColor: PieceColor
) {
    private val aiEvaluationTreeGenerator = AiEvaluationTreeGenerator(level)
    private var root = aiEvaluationTreeGenerator.generate(AiEvaluationNode(aiColor, history), board)

    /**
     * TODO
     */
    fun applyMove(move: Move) {
        root.getChildren().first { it.requiredMove == move }.let { node ->
            root = aiEvaluationTreeGenerator.generate(node, board)
        }
    }

    /**
     * TODO
     */
    fun calculateBestMove() = max(root, Int.MIN_VALUE, Int.MAX_VALUE).requiredMove

    /**
     * TODO
     */
    private fun max(node: AiEvaluationNode, alpha: Int, beta: Int): AiEvaluationNode {
        if (node.numberOfChildren == 0) return node

        var bestVal: AiEvaluationNode? = null
        var bestAlpha = alpha
        for (child in node.getChildren()) {
            val value = min(child, bestAlpha, beta)

            bestVal = maxNode(bestVal, value)
            bestAlpha = bestAlpha.coerceAtLeast(bestVal.valency)

            if (beta <= bestAlpha) break
        }
        return bestVal!!
    }

    /**
     * TODO
     */
    private fun maxNode(node1: AiEvaluationNode?, node2: AiEvaluationNode): AiEvaluationNode {
        return if (node1 == null || node1.valency < node2.valency) node2 else node1
    }

    /**
     * TODO
     */
    private fun min(node: AiEvaluationNode, alpha: Int, beta: Int): AiEvaluationNode {
        if (node.numberOfChildren == 0) return node

        var bestVal: AiEvaluationNode? = null
        var bestBeta = beta
        for (child in node.getChildren()) {
            val value = max(child, alpha, bestBeta)

            bestVal = minNode(bestVal, value)
            bestBeta = bestBeta.coerceAtMost(bestVal.valency)

            if (bestBeta <= alpha) break
        }
        return bestVal!!
    }

    /**
     * TODO
     */
    private fun minNode(node1: AiEvaluationNode?, node2: AiEvaluationNode): AiEvaluationNode {
        return if (node1 == null || node1.valency > node2.valency) node2 else node1
    }
}