package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.piece.PieceSequence
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.service.validator.FieldValidator

object AiEvaluationTreeGenerator {
    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    fun generateChildren(parent: AiEvaluationNode, board: Board, aiColor: PieceColor): MutableList<AiEvaluationNode> {
        val numberOfMove = parent.history.numberOfMoves + 1

        // TODO Parallelling
        val children = PieceSequence.allPiecesByColor(board, aiColor)
                // Create a flatten list of moves for each possible move
                .map { piece -> generateMovesForPiece(board, piece, numberOfMove) }
                .flatten()
                // Create a evaluation for each move
                .map { move -> AiEvaluationNode(parent.history, move, aiColor) }
                // Priority by the position valency
                .sortedBy { it.valency }
                .toMutableList()

        // TODO https://www.lubbe-schach.de/training/goldene-eroeffnungsregeln/
        // TODO 1. Zug Bauer
        if (numberOfMove <= 30) {
            // Sort by priority for the begin of the game
            var sortComparator = compareByDescending<AiEvaluationNode> { FieldValidator.isExtendedCenter(it.requiredMove.to) }
            children.sortWith(sortComparator)
        }

        return children
    }

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    private fun generateMovesForPiece(board: Board, indexedPiece: PieceSequence.IndexedPiece, numberOfMove: Int): MutableList<Move> {
        return indexedPiece.piece
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