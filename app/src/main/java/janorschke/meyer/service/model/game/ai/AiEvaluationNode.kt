package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.utils.BoardUtils.calculatePieceValency
import janorschke.meyer.service.validator.BoardValidator

class AiEvaluationNode {
    val history: History
    val move: Move?
    val aiColor: PieceColor
    val valency: Int
    val priority: Int
    var children: Sequence<AiEvaluationNode>? = null

    val color get() = requiredMove.from.requiredPiece.color
    val requiredMove get() = move!!
    var requiredChildren: Sequence<AiEvaluationNode>
        get() = children!!
        set(children) {
            this.children = children
        }

    constructor(aiEvaluationNode: AiEvaluationNode) {
        this.history = History(aiEvaluationNode.history)
        this.move = aiEvaluationNode.move
        this.aiColor = aiEvaluationNode.aiColor
        this.valency = aiEvaluationNode.valency
        this.priority = aiEvaluationNode.priority
        this.children = aiEvaluationNode.children
    }

    constructor(history: History, move: Move?, aiColor: PieceColor, priority: Int) {
        this.history = History(history)
        this.move = move
        this.aiColor = aiColor
        this.priority = priority
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