package janorschke.meyer.service.model.game.ai.tree

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

/**
 * TODO
 */
abstract class AiEvaluationTree(
        protected val deepness: Int,
        protected val board: Board,
        protected val history: History,
        protected val aiColor: PieceColor
) {
    protected val children: MutableList<AiEvaluationNode>

    init {
        children = generateAiEvaluation(board, aiColor)
                .map { child -> generateAiEvaluation(child, aiColor.opponent(), 1) }
                .toMutableList()
    }

    /**
     * TODO
     */
    abstract fun calculateBestMove(): Move

    /**
     * TODO
     */
    private fun generateAiEvaluation(node: AiEvaluationNode, color: PieceColor, currentDeepness: Int = 0): AiEvaluationNode {
        if (currentDeepness == deepness) return node

        generateAiEvaluation(Board(node.move.fieldsAfterMoving), color, node)
                .map { child -> generateAiEvaluation(child, color.opponent(), currentDeepness + 1) }
                .toMutableList()
                .let { children -> node.addChildren(children) }

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
     * Generate for a piece all possible moves
     *
     * @param indexedPiece which is selected
     * @return all moves of an piece
     */
    private fun generateMoveForPiece(indexedPiece: PieceSequence.IndexedPiece): MutableList<Move> {
        return indexedPiece.piece
                // Get possible moves
                .possibleMoves(board, indexedPiece.position)
                .map { possibleMove ->
                    // Create moves
                    if (BoardValidator.isPawnTransformation(indexedPiece.piece, possibleMove)) {
                        // Special case for the pawn transformation
                        arrayOf(Knight(aiColor), Bishop(aiColor), Rook(aiColor), Queen(aiColor)).map { piece ->
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