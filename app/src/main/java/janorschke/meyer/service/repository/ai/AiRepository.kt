package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
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
abstract class AiRepository(
        val color: PieceColor,
        protected val board: Board,
        protected val history: History,
        val level: AiLevel
) {
    /**
     * @return the next possible move
     */
    abstract fun calculateNextMove(): Move

    /**
     * @param deepness of the simulation tree
     */
    protected fun calculateNextMove(deepness: Int): Move {
        if (deepness < 1) throw IllegalArgumentException("Deepness has to be bigger than 1, but is $deepness")
        if (deepness % 2 != 0) throw IllegalArgumentException("Deepness is invalid")

        AiEvaluationTree(deepness, board, history, color)

        TODO()
    }

    /**
     * TODO
     */
    private fun max(): AiEvaluationNode {
        TODO()
    }

    /**
     * TODO
     */
    private fun min(): AiEvaluationNode {
        TODO()
    }
}