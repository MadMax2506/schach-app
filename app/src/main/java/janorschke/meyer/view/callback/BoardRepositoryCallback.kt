package janorschke.meyer.view.callback

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.move.PossibleMove

interface BoardRepositoryCallback {
    fun openPromotionDialog(pieceColor: PieceColor, possibleMove: PossibleMove)
}
