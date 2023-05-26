package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.AiEvaluationTreeGenerator

/**
 * TODO
 */
class AiEvaluationTree(
        history: History,
        private val level: AiLevel,
        private val board: Board,
        private val aiColor: PieceColor) {
    private var root = AiEvaluationTreeGenerator.generate(AiEvaluationNode(aiColor, history), aiColor, level, board)

    /**
     * TODO
     */
    fun applyMove(move: Move) {
        root = AiEvaluationTreeGenerator.generate(findChildrenOfRoot(move), aiColor, level, board)
    }

    /**
     * TODO
     */
    fun calculateBestMove(): Move {
        TODO()
    }

    private fun findChildrenOfRoot(move: Move) = root.getChildren().first { it.requiredMove() == move }
}