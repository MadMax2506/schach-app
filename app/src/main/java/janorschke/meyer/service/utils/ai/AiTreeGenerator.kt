package janorschke.meyer.service.utils.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceType
import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import janorschke.meyer.service.model.game.ai.AiEvaluationNodeFactory
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.Pawn
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.service.validator.FieldValidator

/**
 * Utility class for the generation of the ai tree
 */
object AiTreeGenerator {
    /**
     * @param parent for the [AiEvaluationNode] which will be generated
     * @param board instance
     * @param color of the current pieces
     * @param aiColor
     * @return a sequence of nodes [AiEvaluationNode]
     */
    fun generateChildren(parent: AiEvaluationNode, board: Board, color: PieceColor, aiColor: PieceColor): Sequence<AiEvaluationNode> {
        return PieceSequence.allPiecesByColor(board, color)
                // Create a flatten list of moves for each possible move
                .map { piece -> generateMovesForPiece(board, parent.history, piece) }
                .flatten()
                // Create a evaluation for each move
                .map { move -> prioritizePieces(parent, move, aiColor) }
    }

    /**
     * Prioritize the pieces in the opening and end game
     *
     * @param parent node
     * @param move on the board
     * @param aiColor
     * @return prioritized evaluation node
     */
    private fun prioritizePieces(parent: AiEvaluationNode, move: Move, aiColor: PieceColor): AiEvaluationNode {
        val history = History(parent.history)
        val factory = AiEvaluationNodeFactory(history, move, aiColor)

        when {
            // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/122
            // Endgame
            // BoardValidator.isEndgame(parent.history) ->

            // First move
            BoardValidator.isFirstMove(parent.history) -> {
                return if (FieldValidator.isCenter(move.to.position) && move.from.piece is Pawn) factory.create(1)
                else factory.create(-1)
            }

            // Opening
            BoardValidator.isOpening(parent.history) -> return openingPiecePrioritization(factory, move, history)

            else -> return factory.create()
        }
    }

    /**
     * **PRIORITY**
     * - `3`: Castling
     * - `2`: Light piece in the extended center | pressure on the center
     * - `1`: Pawn in the extended center
     * - `0`: Default
     * - `-1`: Move same piece
     * - `-2`: Heavy piece move
     * - `-3`: King move
     *
     * @param factory to create a evaluation move
     * @param move of a piece
     * @param history instance
     * @return prioritized evaluation node
     */
    private fun openingPiecePrioritization(factory: AiEvaluationNodeFactory, move: Move, history: History): AiEvaluationNode {
        return when {
            // Castling
            move.castling != null -> factory.create(3)

            // Move the king
            move.from.requiredPiece is King -> factory.create(-3)

            // Move a heavy piece
            move.from.requiredPiece.pieceInfo.type == PieceType.HEAVY -> factory.create(-2)

            // Move a piece multiple times
            history.getLastMoves(history.numberOfMoves.coerceAtMost(12))
                    .any { move.from.requiredPiece == it.from.requiredPiece } -> factory.create(-1)

            // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/123
            // priority = 2
            // Pressure on the center

            // Place pieces in the extended center
            FieldValidator.isExtendedCenter(move.to.position) -> {
                if (move.from.requiredPiece.pieceInfo.type == PieceType.LIGHT) factory.create(2) else factory.create(1)
            }

            else -> factory.create(0)
        }
    }

    /**
     * @param board instance
     * @param indexedPiece which is on the board
     * @return all possible moves of the given piece
     */
    private fun generateMovesForPiece(board: Board, history: History, indexedPiece: PieceSequence.IndexedPiece): Sequence<Move> {
        return indexedPiece.piece
                .possibleMoves(board, history, indexedPiece.position)
                .map { possibleMove ->
                    // Create moves
                    if (BoardValidator.isPawnTransformation(indexedPiece.piece, possibleMove.to.position)) {
                        // Special case for the pawn transformation
                        val color = indexedPiece.piece.color
                        sequenceOf(Knight(color), Bishop(color), Rook(color), Queen(color)).map { piece ->
                            Board(board).createMove(indexedPiece.position, possibleMove.to.position, piece)
                        }
                    } else {
                        // Normal move
                        sequenceOf(Board(board).createMove(indexedPiece.position, possibleMove.to.position))
                    }
                }
                .flatten()
    }
}