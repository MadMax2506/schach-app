package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

class AiEvaluationTree(private val deepness: Int, private val board: Board, private val history: History, private val color: PieceColor) {
    private val children: MutableList<AiEvaluationNode>

    init {
        children = generateAiEvaluation(board, color)
                .map { child -> generateAiEvaluation(child, color.opponent()) }
                .toMutableList()
    }

    /**
     * TODO
     */
    fun getLeafs() = children.map { getLeafs(it) }.flatten().toMutableList()

    /**
     * TODO
     */
    private fun getLeafs(node: AiEvaluationNode): MutableList<AiEvaluationNode> {
        if (node.getChildren().isEmpty()) return mutableListOf(node)
        return node.getChildren().map { getLeafs(it) }.flatten().toMutableList()
    }

    /**
     * TODO
     */
    private fun generateAiEvaluation(node: AiEvaluationNode, color: PieceColor, currentDeepness: Int = 0): AiEvaluationNode {
        if (currentDeepness == deepness) return node

        generateAiEvaluation(Board(node.move.fieldsAfterMoving), color, node)
                .map { child -> generateAiEvaluation(child, color.opponent(), currentDeepness + 1) }
                .toMutableList()
                .let { node.addChildren(it) }

        return node
    }

    /**
     * Generate for the next move of an color an ai evaluation
     *
     * @param board instance
     * @param color of the pieces
     * @param parent node
     * @return a list of all moves
     */
    private fun generateAiEvaluation(board: Board, color: PieceColor, parent: AiEvaluationNode? = null): MutableList<AiEvaluationNode> {
        return PieceSequence.allPiecesByColor(board, color)
                // Create a flatten list of moves for each possible move
                .map { generateMoveForPiece(it) }
                .flatten()
                // Create a evaluation for each move
                .map { move ->
                    if (parent == null) AiEvaluationNode(move, color, History(history))
                    else AiEvaluationNode(move, color, parent)
                }
                .toMutableList()
    }

    /**
     * @param indexedPiece which is selected
     * @return all possible moves of an piece
     */
    private fun generateMoveForPiece(indexedPiece: PieceSequence.IndexedPiece): MutableList<Move> {
        return indexedPiece.piece
                // Get possible moves
                .possibleMoves(board, indexedPiece.position)
                .map { possibleMove ->
                    // Create moves
                    if (BoardValidator.isPawnTransformation(indexedPiece.piece, possibleMove)) {
                        // Special case for the pawn transformation
                        arrayOf(Knight(color), Bishop(color), Rook(color), Queen(color)).map { piece ->
                            Board(board).createMove(indexedPiece.position, possibleMove, piece)
                        }.toMutableList()
                    } else {
                        // Normal move
                        mutableListOf(Board(board).createMove(indexedPiece.position, possibleMove))
                    }
                }
                .flatten()
                .toMutableList()
    }
}