package janorschke.meyer.service.xml

import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Piece
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class HistoryXml : BaseXml {
    @XmlElementWrapper(name = "beatenPieces")
    @XmlElement(name = "piece")
    var beatenPieces: ArrayList<Piece>?

    @XmlElementWrapper(name = "moves")
    @XmlElement(name = "move")
    var moves: ArrayList<Move>?

    // Needed for XML serialization
    constructor() {
        beatenPieces = null
        moves = null
    }

    constructor(history: History) {
        beatenPieces = listToArrayList(history.getBeatenPieces())
        moves = listToArrayList(history.getMoves())
    }
}