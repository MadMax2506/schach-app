package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationTree
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 *
 * @param color of the ai
 * @param board instance
 * @param history instance
 * @param level of the ai
 */
abstract class AiRepository(val color: PieceColor, board: Board, history: History, val level: AiLevel) {
    val evaluationTree: AiEvaluationTree

    init {
        if (level.deepness < 1 || level.deepness % 2 != 0) throw IllegalArgumentException("Deepness is invalid")
        evaluationTree = AiEvaluationTree(level, board, color)
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