package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.piece.PieceSequence

private const val LOG_TAG = "AiLevelThreeRepository"

/**
 * Represents a heavy ai
 * @see AiLevel.CHRIS
 */
class AiLevelThreeRepository(color: PieceColor, board: Board) : AiRepository(color, board, AiLevel.CHRIS) {
    override fun calculateNextMove(): Move {
        Board(board).let { boardCopy ->
            // TODO
            // val boardEvaluation = super.evaluateBoard(this)

            Log.d(LOG_TAG, "Calculate the next move for $aiLevel")

            // TODO temporary code part
            val temp = PieceSequence.allPiecesByColor(boardCopy, color)
                    .filter { it.piece.possibleMoves(boardCopy, it.position).isNotEmpty() }
                    .map { Pair(it.position, it.piece.possibleMoves(boardCopy, it.position).first()) }
                    .first()
            return boardCopy.createBoardMove(temp.first, temp.second)
        }
    }
}