package janorschke.meyer.view.callback

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.Move

interface BoardRepositoryCallback {
    fun openPromotionDialog(fromPosition: Position, toPosition: Position, pieceColor: PieceColor): Move
}
