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
    val priority: Int
    val valency: Int
    var children: Sequence<AiEvaluationNode>?

    val color get() = requiredMove.from.requiredPiece.color
    val requiredMove get() = move!!
    var requiredChildren: Sequence<AiEvaluationNode>
        get() = children!!
        set(children) {
            this.children = children
        }

    constructor(aiEvaluationNode: AiEvaluationNode) {
        this.history = History(aiEvaluationNode.history)
        this.move = if (aiEvaluationNode.move != null) Move(aiEvaluationNode.move) else null
        this.aiColor = aiEvaluationNode.aiColor
        this.priority = aiEvaluationNode.priority
        this.valency = aiEvaluationNode.valency
        this.children = if (aiEvaluationNode.children != null) aiEvaluationNode.children!!.map { AiEvaluationNode(it) } else null
    }

    constructor(history: History, move: Move?, aiColor: PieceColor, priority: Int) {
        this.history = History(history)
        this.move = move
        this.aiColor = aiColor
        this.priority = priority
        this.children = null
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