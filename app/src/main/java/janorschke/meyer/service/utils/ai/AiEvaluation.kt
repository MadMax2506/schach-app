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
    ): Int {
        if (currentDepth == depth) return parent.valency

        val board = Board(parent.requiredMove.fieldsAfterMoving)
        val maximizingPlayer = (color == aiColor)

        var bestVal = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        var mutableAlpha = alpha
        var mutableBeta = beta

        for (child in generateChildren(parent, board, color, aiColor)) {
            val calcVal = minimax(
                    parent = child,
                    depth = depth,
                    currentDepth = currentDepth + 1,
                    alpha = mutableAlpha,
                    beta = mutableBeta,
                    color = color.opponent(),
                    aiColor = aiColor
            )

            if (maximizingPlayer) {
                bestVal = bestVal.coerceAtLeast(calcVal)
                mutableAlpha = mutableAlpha.coerceAtLeast(bestVal)
            } else {
                bestVal = bestVal.coerceAtMost(calcVal)
                mutableBeta = mutableBeta.coerceAtMost(bestVal)
            }

            if (mutableBeta <= mutableAlpha) break
        }

        return bestVal
    }
}