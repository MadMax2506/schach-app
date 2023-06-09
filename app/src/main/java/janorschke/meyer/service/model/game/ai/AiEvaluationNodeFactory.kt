package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move

/**
 * Create a evaluation node depending on the priority
 *
 * @param history instance
 * @param move of the piece
 * @param aiColor
 */
class AiEvaluationNodeFactory(private val history: History, private val move: Move, private val aiColor: PieceColor) {
    /**
     * @param priority of the move - **DEFAULT**: 0
     * @return evaluation node
     */
    fun create(priority: Int = 0) = AiEvaluationNode(history, move, aiColor, priority)
}