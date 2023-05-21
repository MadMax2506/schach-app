package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

/**
 * @param move which is done in the evaluation step
 * @param aiColor
 * @param history instance
 * @param parent node
 */
class AiEvaluationNode(val move: Move, aiColor: PieceColor, private val history: History, val parent: AiEvaluationNode?) {
    val valency: Int

    private val children: MutableList<AiEvaluationNode> = mutableListOf()

    constructor(move: Move, aiColor: PieceColor, parent: AiEvaluationNode) : this(move, aiColor, parent.history, parent)
    constructor(move: Move, aiColor: PieceColor, history: History) : this(move, aiColor, history, null)

    init {
        // Calculates valency of the current position
        valency = if (aiColor == PieceColor.WHITE) 1 else -1 * Board(move.fieldsAfterMoving).let { boardCopy ->
            history.push(move)

            val color = move.fromPiece().color.opponent()

            if (BoardValidator.isKingCheckmate(boardCopy, color)) Int.MAX_VALUE
            if (BoardValidator.isStalemate(boardCopy, history, color)) Int.MIN_VALUE
            getPieceValue(boardCopy, PieceColor.WHITE) - getPieceValue(boardCopy, PieceColor.BLACK)
        }
    }

    /**
     * TODO
     */
    fun addChildren(children: MutableList<AiEvaluationNode>) {
        this.children.plus(children)
    }

    /**
     * TODO
     */
    fun getChildren() = children

    /**
     * @param board instance
     * @param color of the related pieces
     * @return the valence of all pieces by the given color
     */
    private fun getPieceValue(board: Board, color: PieceColor): Int {
        return PieceSequence
                .allPiecesByColor(board, color)
                .filter { indexedPiece -> indexedPiece.piece !is King }
                .sumOf { indexedPiece -> indexedPiece.piece.pieceInfo.valence }
    }
}