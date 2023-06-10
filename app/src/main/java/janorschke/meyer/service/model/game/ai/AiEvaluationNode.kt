package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.utils.BoardUtils.calculatePieceValency
import janorschke.meyer.service.validator.BoardValidator

class AiEvaluationNode(history: History, val move: Move?, val aiColor: PieceColor, val priority: Int) {
    val history: History
    val valency: Int
    var children: Sequence<AiEvaluationNode>? = null

    val color get() = requiredMove.from.requiredPiece.color
    val requiredMove get() = move!!
    var requiredChildren: Sequence<AiEvaluationNode>
        get() = children!!
        set(children) {
            this.children = children
        }

    init {
        this.history = History(history)
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