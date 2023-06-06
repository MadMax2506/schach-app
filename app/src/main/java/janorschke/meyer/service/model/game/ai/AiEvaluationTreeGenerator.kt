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

object AiEvaluationTreeGenerator {
    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    fun generateChildren(parent: AiEvaluationNode, board: Board, aiColor: PieceColor): MutableList<AiEvaluationNode> {
        return PieceSequence.allPiecesByColor(board, aiColor)
                // Create a flatten list of moves for each possible move
                .map { piece -> generateMovesForPiece(board, parent.history, piece) }
                .flatten()
                // Create a evaluation for each move
                .map { move -> AiEvaluationNode(aiColor, move, parent.history) }
                .toMutableList()
    }

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
     */
    private fun generateMovesForPiece(board: Board, history: History, indexedPiece: PieceSequence.IndexedPiece): MutableList<Move> {
        return indexedPiece.piece
                // Get possible moves
                .possibleMoves(board, history, indexedPiece.position)
                .map { possibleMove ->
                    // Create moves
                    val to = possibleMove.toPosition
                    
                    if (BoardValidator.isPawnTransformation(indexedPiece.piece, to)) {
                        // Special case for the pawn transformation
                        val color = indexedPiece.piece.color
                        arrayOf(Knight(color), Bishop(color), Rook(color), Queen(color)).map { piece ->
                            Board(board).createMove(indexedPiece.position, to, piece)
                        }.toMutableList()
                    } else {
                        // Normal move
                        mutableListOf(Board(board).createMove(possibleMove))
                    }
                }
                .flatten()
                .toMutableList()
    }
}