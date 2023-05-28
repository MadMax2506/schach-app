package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move

/**
 * TODO
 */
class AiEvaluationTree(
        // TODO
        private val level: AiLevel,
        private val board: Board,
        // TODO
        private val aiColor: PieceColor
) {
    private val aiEvaluationTreeGenerator = AiEvaluationTreeGenerator(level)
    private var root = aiEvaluationTreeGenerator.generate(AiEvaluationNode(aiColor), board)

    fun getRoot() = root

    /**
     * TODO
     */
    fun applyMove(move: Move) {
        root.requiredChildren().first { it.requiredMove == move }.let { node ->
            root = aiEvaluationTreeGenerator.generate(node, board)
        }
    }

    /**
     * TODO
     */
    // TODO check algorithm, because it does not return the correct res
    fun calculateBestMove() = max(root, Int.MIN_VALUE, Int.MAX_VALUE).requiredMove

    /**
     * TODO
     */
    private fun max(parent: AiEvaluationNode, alpha: Int, beta: Int): AiEvaluationNode {
        if (parent.numberOfChildren == 0) return parent

        var bestNode: AiEvaluationNode? = null
        var mutableAlpha = alpha
        for (child in parent.requiredChildren()) {
            val calcNode = min(child, mutableAlpha, beta)

            bestNode = maxNode(bestNode, calcNode)
            mutableAlpha = mutableAlpha.coerceAtLeast(bestNode.valency)

            if (beta <= mutableAlpha) break
        }
        return bestNode!!
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
    private fun min(parent: AiEvaluationNode, alpha: Int, beta: Int): AiEvaluationNode {
        if (parent.numberOfChildren == 0) return parent

        var bestNode: AiEvaluationNode? = null
        var mutableBeta = beta
        for (child in parent.requiredChildren()) {
            val calcNode = max(child, alpha, mutableBeta)

            bestNode = minNode(bestNode, calcNode)
            mutableBeta = mutableBeta.coerceAtMost(bestNode.valency)

            if (mutableBeta <= alpha) break
        }
        return bestNode!!
    }

    /**
     * TODO
     */
    private fun minNode(node1: AiEvaluationNode?, node2: AiEvaluationNode): AiEvaluationNode {
        return if (node1 == null || node1.valency > node2.valency) node2 else node1
    }
}