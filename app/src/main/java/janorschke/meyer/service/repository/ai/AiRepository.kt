package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiBoardEvaluation
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.piece.PieceSequence

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 */
abstract class AiRepository(val color: PieceColor, protected val board: Board, val aiLevel: AiLevel) {
    /**
     * @return the next possible move
     */
    abstract fun calculateNextMove(): Move

    /**
     * @return all evaluated moves for the ai
     */
    protected fun evaluateBoard(): MutableList<AiBoardEvaluation> {
        Log.d(LOG_TAG, "Evaluate the board")

        TODO("evaluateBoard is not implemented")
        generateMoves().map { move -> moveEvaluation(move) }
    }

    private fun generateMoves(): MutableList<Move> {
        return PieceSequence.allPiecesByColor(board, color)
                .map { indexedPiece ->
                    indexedPiece.piece
                            .possibleMoves(board, indexedPiece.position)
                            .map { possibleMove -> Board(board).createBoardMove(indexedPiece.position, possibleMove) }
                }
                .flatten()
                .toMutableList()
    }

    private fun moveEvaluation(move: Move) {
        TODO("moveEvaluation https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/")
    }

    private fun searchTreeMinMax() {
        TODO("searchTreeMinMax https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/")
    }

    private fun alphaBetaTrimming() {
        TODO("alphaBetaTrimming https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/")
    }
}