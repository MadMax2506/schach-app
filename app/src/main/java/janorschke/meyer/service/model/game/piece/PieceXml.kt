package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.BaseXml
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PieceXml : BaseXml {
    var color: PieceColor?
    var pieceInfo: PieceInfo?

    // Needed for XML serialization
    constructor() {
        color = null
        pieceInfo = null
    }

    constructor(piece: Piece) {
        this.color = piece.color
        this.pieceInfo = piece.pieceInfo
    }

    fun toPiece() = when (pieceInfo!!) {
        PieceInfo.PAWN -> Pawn(color!!)
        PieceInfo.ROOK -> Rook(color!!)
        PieceInfo.KNIGHT -> Knight(color!!)
        PieceInfo.BISHOP -> Bishop(color!!)
        PieceInfo.QUEEN -> Queen(color!!)
        PieceInfo.KING -> King(color!!)
    }
}