package janorschke.meyer.service.xml

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.piece.Piece
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO
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
}