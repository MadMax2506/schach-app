package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationTree
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 *
 * @param color of the ai
 * @param level of the ai
 * @param board instance
 */
abstract class AiRepository(val color: PieceColor, level: AiLevel, board: Board, readFromFile: Boolean = false) {
    val evaluationTree: AiEvaluationTree

    init {
        if (level.depth < 1 || level.depth % 2 != 0) throw IllegalArgumentException("Depth is invalid")
        evaluationTree = AiEvaluationTree(color, level, readFromFile, board)
    }

    /**
     * @see AiEvaluationTree.applyMove
     */
    fun applyMove(move: Move) = evaluationTree.applyMove(move)

    /**
     * @return the next possible move
     */
    fun calculateNextMove() = evaluationTree.calculateBestMove()
}