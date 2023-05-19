package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.piece.PieceSequence

private const val LOG_TAG = "AiLevelTwoRepository"

/**
 * Represents a medium ai
 * @see AiLevel.MAX
 */
class AiLevelTwoRepository(color: PieceColor, board: Board) : AiRepository(color, board, AiLevel.MAX) {
    override fun calculateNextMove(): Move {
        Board(board).let { boardSimulation ->
            // TODO
            // val boardEvaluation = super.evaluateBoard(this)

            Log.d(LOG_TAG, "Calculate the next move for $aiLevel")

            // TODO temporary code part
            val temp = PieceSequence.allPiecesByColor(boardSimulation, color)
                    .filter { it.piece.possibleMoves(boardSimulation, it.position).isNotEmpty() }
                    .map { Pair(it.position, it.piece.possibleMoves(boardSimulation, it.position).first()) }
                    .first()
            return boardSimulation.createBoardMove(temp.first, temp.second)
        }
    }
}