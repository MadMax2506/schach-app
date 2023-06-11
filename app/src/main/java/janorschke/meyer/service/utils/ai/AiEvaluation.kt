package janorschke.meyer.service.utils.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.utils.ai.AiTreeGenerator.generateChildren

object AiEvaluation {
    /**
     * @param parent [AiEvaluationNode]
     * @param currentDepth of the tree
     * @param alpha value to optimize the [AiEvaluation.minimax]-algorithm
     * @param beta value to optimize the [AiEvaluation.minimax]-algorithm
     * @param color of the pieces for the current depth
     * @return best [AiEvaluationNode.valency] of the evaluated children
     */
    fun minimax(
            parent: AiEvaluationNode,
            currentDepth: Int,
            depth: Int,
            alpha: Int,
            beta: Int,
            color: PieceColor,
            aiColor: PieceColor
    ): AiEvaluationNode {
        if (currentDepth == depth) return parent

        val board = Board(parent.requiredMove.fieldsAfterMoving)
        val maximizingPlayer = (color == aiColor)

        var bestNode: AiEvaluationNode? = null
        var mutableAlpha = alpha
        var mutableBeta = beta

        if (parent.children == null) {
            parent.requiredChildren = generateChildren(parent, board, color, aiColor)
        }

        for (child in parent.requiredChildren) {
            val calcNode = minimax(
                    parent = child,
                    depth = depth,
                    currentDepth = currentDepth + 1,
                    alpha = mutableAlpha,
                    beta = mutableBeta,
                    color = color.opponent(),
                    aiColor = aiColor
            )

            if (maximizingPlayer) {
                bestNode = maxNode(bestNode, calcNode)
                mutableAlpha = mutableAlpha.coerceAtLeast(bestNode.valency)
            } else {
                bestNode = minNode(bestNode, calcNode)
                mutableBeta = mutableBeta.coerceAtMost(bestNode.valency)
            }

            if (mutableBeta <= mutableAlpha) break
        }

        return bestNode!!
    }

    private fun minNode(node1: AiEvaluationNode?, node2: AiEvaluationNode): AiEvaluationNode {
        return if (node1 == null || node1.valency > node2.valency) node2 else node1
    }

    private fun maxNode(node1: AiEvaluationNode?, node2: AiEvaluationNode): AiEvaluationNode {
        return if (node1 == null || node1.valency < node2.valency) node2 else node1
    }
}