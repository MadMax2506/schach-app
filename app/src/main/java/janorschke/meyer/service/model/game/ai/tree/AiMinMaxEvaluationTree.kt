package janorschke.meyer.service.model.game.ai.tree

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move

/**
 * Evaluate the search tree with help of the min max algorithm
 *
 * @see AiEvaluationTree
 */
class AiMinMaxEvaluationTree(
        deepness: Int,
        board: Board,
        history: History,
        color: PieceColor
) : AiEvaluationTree(deepness, board, history, color) {
    override fun calculateBestMove(): Move {
        TODO("Not yet implemented")
    }

    /**
     * TODO
     */
    private fun max(): AiEvaluationNode {
        TODO("https://de.wikipedia.org/wiki/Minimax-Algorithmus")
    }

    /**
     * TODO
     */
    private fun min(): AiEvaluationNode {
        TODO("https://de.wikipedia.org/wiki/Minimax-Algorithmus")
    }
}