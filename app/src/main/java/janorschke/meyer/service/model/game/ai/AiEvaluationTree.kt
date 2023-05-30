package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move

/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
 */
class AiEvaluationTree(color: PieceColor, level: AiLevel, readFromFile: Boolean, private val board: Board) {
    private val aiEvaluationTreeGenerator = AiEvaluationTreeGenerator(level)
    private var root: AiEvaluationNode

    init {
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/106
        root = /*if (readFromFile) XmlSerialisation.toObject<AiEvaluationNodeXml>(DIR, getAi(color, level)).toNode(color)
        else*/ aiEvaluationTreeGenerator.generate(AiEvaluationNode(color), board)
    }

    fun getRoot() = root

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    fun applyMove(move: Move) {
        root.requiredChildren()
                .first { node -> node.requiredMove == move }
                .let { node -> root = aiEvaluationTreeGenerator.generate(node, board) }
    }

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
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
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
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
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
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