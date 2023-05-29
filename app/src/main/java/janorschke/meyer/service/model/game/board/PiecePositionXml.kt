package janorschke.meyer.service.model.game.board

import janorschke.meyer.service.model.BaseXml
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