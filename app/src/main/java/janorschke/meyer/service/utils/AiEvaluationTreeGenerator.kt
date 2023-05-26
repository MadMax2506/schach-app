package janorschke.meyer.service.utils

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator

object AiEvaluationTreeGenerator {
    /**
     * TODO
     */
    fun generate(node: AiEvaluationNode, color: PieceColor, level: AiLevel, board: Board): AiEvaluationNode {
        return generate(node, color, level, 0, board)
    }

    /**
     * TODO
     */
    private fun generate(
            node: AiEvaluationNode,
            color: PieceColor,
            level: AiLevel,
            currentDeepness: Int,
            board: Board = Board(node.requiredMove().fieldsAfterMoving)
    ): AiEvaluationNode {
        if (currentDeepness == level.deepness || BoardValidator.isKingCheckmate(board, color.opponent())) {
            return node
        }

        // TODO
        (if (node.getChildren().size > 0) node.getChildren() else generateChildren(node, board, color))
                .map { child -> generate(child, color.opponent(), level, currentDeepness + 1) }
                .toMutableList()
                .let { children -> node.addChildren(children) }

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