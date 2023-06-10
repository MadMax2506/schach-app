package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.utils.BoardUtils.calculatePieceValency
import janorschke.meyer.service.validator.BoardValidator

const val LOG_TAG = "AiEvaluationNode"

/**
 * @param history instance
 * @param move of the current evaluation step
 * @param aiColor
 * @param priority in the evaluation cycle
 */
class AiEvaluationNode(val history: History, val move: Move?, private val aiColor: PieceColor, val priority: Int) {
    val valency: Int

    val requiredMove get() = move!!
    val color get() = requiredMove.from.requiredPiece.color

    init {
        this.valency = if (move == null) {
            // Neutral starting position on the board
            0
        } else {
            // Calculates valency of the current position
            Board(move.fieldsAfterMoving).let { boardCopy ->
                history.push(move)

                if (BoardValidator.isKingCheckmate(boardCopy, history, color.opponent())) return@let Int.MAX_VALUE
                return@let calculatePieceValency(boardCopy, aiColor) - calculatePieceValency(boardCopy, aiColor.opponent())
            }
        }
    }
}