package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

class AiEvaluationTreeGenerator(private val level: AiLevel) {
    /**
     * TODO
     */
    fun generate(root: AiEvaluationNode, board: Board): AiEvaluationNode {
        // If the root node contains no move, it is the start of the game (WHITE) begins
        // otherwise the opponent color is the next one
        val color = if (root.move == null) PieceColor.WHITE else root.requiredMove.fromPiece.color.opponent()
        return generate(root, color, 0, board)
    }

    /**
     * TODO
     */
    private fun generate(
            node: AiEvaluationNode,
            color: PieceColor,
            currentDeepness: Int,
            board: Board = Board(node.requiredMove.fieldsAfterMoving)
    ): AiEvaluationNode {
        if (currentDeepness == level.deepness || BoardValidator.isKingCheckmate(board, color.opponent())) return node

        // TODO
        val children = (if (node.numberOfChildren > 0) node.getChildren() else generateChildren(node, board, color))
                .map { child -> generate(child, color.opponent(), currentDeepness + 1) }
                .toMutableList()

        node.setChildren(children)
        return node
    }

    /**
     * TODO
     */
    private fun generateChildren(parent: AiEvaluationNode, board: Board, color: PieceColor): MutableList<AiEvaluationNode> {
        return PieceSequence
                .allPiecesByColor(board, color)
                // Create a flatten list of moves for each possible move
                .map { piece -> generateMovesForPiece(board, piece) }
                .flatten()
                // Create a evaluation for each move
                .map { move -> AiEvaluationNode(color, move, parent) }
                .toMutableList()
    }

    /**
     * TODO
     */
    private fun generateMovesForPiece(board: Board, indexedPiece: PieceSequence.IndexedPiece): MutableList<Move> {
        return indexedPiece.piece
                // Get possible moves
                .possibleMoves(board, indexedPiece.position)
                .map { possibleMove ->
                    // Create moves
                    if (BoardValidator.isPawnTransformation(indexedPiece.piece, possibleMove)) {
                        // Special case for the pawn transformation
                        val color = indexedPiece.piece.color
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