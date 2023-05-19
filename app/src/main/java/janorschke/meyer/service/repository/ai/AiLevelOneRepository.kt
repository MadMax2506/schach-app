package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.piece.PieceSequence

private const val LOG_TAG = "AiLevelOneRepository"

/**
 * Represents a simple ai
 * @see AiLevel.KEVIN_OTTO
 */
class AiLevelOneRepository(color: PieceColor) : AiRepository(color, AiLevel.KEVIN_OTTO) {
    override fun calculateNextMove(board: Board): Move {
        Board(board).apply {
            // TODO
            // val boardEvaluation = super.evaluateBoard(this)

            Log.d(LOG_TAG, "Calculate the next move for $aiLevel")

            // TODO temporary code part
            val temp = PieceSequence.allPiecesByColor(this, color)
                    .map { Pair(it.position, it.piece.possibleMoves(this, it.position).first()) }
                    .first()
            return this.createBoardMove(temp.first, temp.second)
        }
    }
}