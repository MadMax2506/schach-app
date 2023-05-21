package janorschke.meyer.service.model.game.ai.tree

import janorschke.meyer.enums.AiEvaluationType
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History

/**
 * @param aiEvaluationType of the evaluation algorithm
 * @see AiEvaluationTree
 */
class AiEvaluationTreeFactory(
        private val aiEvaluationType: AiEvaluationType,
        private val deepness: Int,
        private val board: Board,
        private val history: History,
        private val aiColor: PieceColor
) {
    /**
     * Create an instance of the evaluation tree
     *
     * @return instance of the evaluation tree
     */
    fun create(): AiEvaluationTree {
        return when (aiEvaluationType) {
            AiEvaluationType.MIN_MAX_EVALUATION -> AiMinMaxEvaluationTree(deepness, board, history, aiColor)
            AiEvaluationType.ALPHA_BETA_PRUNING -> AiAlphaBetaPruningEvaluationTree(deepness, board, history, aiColor)
        }
    }
}