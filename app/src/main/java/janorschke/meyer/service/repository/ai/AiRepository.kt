package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationTree
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 *
 * @param color of the ai
 * @param board instance
 * @param deepness of the evaluation tree
 */
open class AiRepository(val color: PieceColor, board: Board, deepness: Int) {
    val evaluationTree: AiEvaluationTree

    init {
        if (deepness < 1 || deepness % 2 != 0) throw IllegalArgumentException("Deepness is invalid")
        evaluationTree = AiEvaluationTree(deepness, board, color)
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