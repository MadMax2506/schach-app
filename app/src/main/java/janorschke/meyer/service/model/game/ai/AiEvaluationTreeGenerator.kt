package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceType
import janorschke.meyer.service.model.game.AiEvaluationNode
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.Pawn
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
        val numberOfMove = parent.history.numberOfMoves
        var pieceSequence = PieceSequence.allPiecesByColor(board, aiColor)

        // Do the first move with a pawn
        if (numberOfMove <= 2) pieceSequence = pieceSequence.filter { it.piece is Pawn }

        return pieceSequence
                // Create a flatten list of moves for each possible move
                .map { piece -> generateMovesForPiece(board, piece) }
                .flatten()
                // Create a evaluation for each move
                .map { move -> AiEvaluationNode(parent.history, move, aiColor) }
                .sortedWith(prioritizePieces(parent, numberOfMove))
                .toMutableList()
    }

    /**
     * Prioritize the pieces in the opening and end game
     *
     * @param parent node
     * @param numberOfMove which are currently done
     * @return the comparator for prioritization
     */
    private fun prioritizePieces(parent: AiEvaluationNode, numberOfMove: Int): Comparator<AiEvaluationNode> {
        return when {
            // First move
            numberOfMove <= 2 -> {
                // Place pieces in the center in the first move
                compareByDescending { FieldValidator.isCenter(it.requiredMove.to) }
            }
            // Opening
            numberOfMove <= 30 -> {
                // TODO https://www.lubbe-schach.de/training/goldene-eroeffnungsregeln/
                // TODO Druck Zentrum
                // TODO Rochade bevorzugen: https://github.com/MadMax2506/android-wahlmodul-project/issues/115
                // Move in the last 3 moves not the same piece
                compareByDescending<AiEvaluationNode> { node -> parent.history.getLastMoves(6).none { node.requiredMove.from == it.to } }
                        .thenByDescending { it.requiredMove.fromPiece.pieceInfo.type != PieceType.HEAVY }
                        // Place pieces in the extended center in the first 15 moves
                        .thenByDescending { FieldValidator.isExtendedCenter(it.requiredMove.to) }
                        // Move queen not in the opening
                        .thenBy { it.requiredMove.fromPiece is Queen || it.requiredMove.fromPiece is King }
            }
            // TODO https://de.wikipedia.org/wiki/Endspiel_(Schach)
            else -> Comparator.comparing { 0 }
        }
    }

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    private fun generateMovesForPiece(board: Board, indexedPiece: PieceSequence.IndexedPiece): Sequence<Move> {
        return indexedPiece.piece
                .possibleMoves(board, indexedPiece.position)
                .asSequence()
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
    }
}