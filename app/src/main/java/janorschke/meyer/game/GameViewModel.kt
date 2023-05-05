package janorschke.meyer.game

import androidx.lifecycle.ViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PiecePosition

class GameViewModel : ViewModel() {
    val board: Board = Board(this)

    fun movePiece(from: PiecePosition, to: PiecePosition) {
        TODO("Move piece")
    }

    fun onFieldClicked(position: PiecePosition) {
        TODO("Validierung, ob ein eigenes Piece angeklickt => in einen State behalten und beim nächsten klick schauen, ob der Move valide ist")
    }
}
