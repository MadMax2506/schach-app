package janorschke.meyer.service.xml

import janorschke.meyer.service.utils.board.PiecePosition
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PiecePositionXml : BaseXml {
    var row: Int?
    var col: Int?

    // Needed for XML serialization
    constructor() {
        row = null
        col = null
    }

    constructor(piecePosition: PiecePosition) {
        this.row = piecePosition.row
        this.col = piecePosition.col
    }
}