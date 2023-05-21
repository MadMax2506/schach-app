package janorschke.meyer.service.model.game.ai.tree

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move

/**
 * Evaluate the search tree with help of the optimized min max algorithm alpha beta pruning
 *
 * @see AiEvaluationTree
 */
class AiAlphaBetaPruningEvaluationTree(
        deepness: Int,
        board: Board,
        history: History,
        color: PieceColor
) : AiEvaluationTree(deepness, board, history, color) {
    override fun calculateBestMove(): Move {
        TODO("Not yet implemented")
    }
}