package janorschke.meyer.service.model.board

import janorschke.meyer.service.model.Move
import janorschke.meyer.service.model.piece.Pawn
import janorschke.meyer.service.model.piece.lineMoving.Queen
import janorschke.meyer.service.utils.board.PiecePosition

class BoardCopy(board: Board) : Board() {
    init {
        fields = board.getFields().map { it.copyOf() }.toTypedArray()
    }

    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     * @return board move
     */
    fun createBoardMove(from: PiecePosition, to: PiecePosition): Move {
        val fromPiece = getField(from)!!
        val toPiece = getField(to)

        setField(from, null)
        if (fromPiece is Pawn && to.row == fromPiece.color.opponent().borderlineIndex) {
            setField(to, Queen(fromPiece.color))
        } else {
            setField(to, fromPiece)
        }

        return Move(fields.map { it.copyOf() }.toTypedArray(), from, to, fromPiece, toPiece)
    }
}