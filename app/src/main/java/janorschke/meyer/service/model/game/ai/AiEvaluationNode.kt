package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

/**
 * TODO
 */
class AiEvaluationNode(aiColor: PieceColor, val parent: AiEvaluationNode?, private val move: Move?, private val history: History) {
    val valency: Int

    private val children: MutableList<AiEvaluationNode> = mutableListOf()

    // Constructor for the begin of an game
    constructor(aiColor: PieceColor, history: History) : this(aiColor, null, null, History(history))

    // Default constructor for a node or leaf
    constructor(aiColor: PieceColor, move: Move, parent: AiEvaluationNode) : this(aiColor, parent, move, parent.history)

    init {
        valency = if (move == null) {
            // Neutral starting position on the board
            0
        } else {
            // Calculates valency of the current position
            if (aiColor == PieceColor.WHITE) 1 else -1 * Board(move.fieldsAfterMoving).let { boardCopy ->
                history.push(move)

                val color = move.fromPiece().color.opponent()

                if (BoardValidator.isKingCheckmate(boardCopy, color)) Int.MAX_VALUE
                if (BoardValidator.isStalemate(boardCopy, history, color)) Int.MIN_VALUE
                getPieceValue(boardCopy, PieceColor.WHITE) - getPieceValue(boardCopy, PieceColor.BLACK)
            }
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
     * TODO
     */
    fun requiredMove() = move!!

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