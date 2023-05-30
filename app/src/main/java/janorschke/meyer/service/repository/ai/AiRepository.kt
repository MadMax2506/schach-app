package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationTree
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History

/**
 * Handles the ai moves
 *
 * @param color of the ai
 * @param level of the ai
 */
abstract class AiRepository(val color: PieceColor, level: AiLevel) {
    private val evaluationTree: AiEvaluationTree

    init {
        if (level.depth < 0 || level.depth % 2 != 0) throw IllegalArgumentException("Depth is invalid")
        evaluationTree = AiEvaluationTree(color, level)
    }

    /**
     * @return the next possible move
     */
    fun calculateNextMove(board: Board, history: History) = evaluationTree.calculateBestMove(board, history)
}