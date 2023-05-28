package janorschke.meyer.service.xml

import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Piece
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class MoveXml : BaseXml {
    var fieldsAfterMoving: Array<Array<PieceXml?>>?
    var from: PiecePositionXml?
    var to: PiecePositionXml?
    var fromPiece: PieceXml?
    var toPiece: PieceXml?

    // Needed for XML serialization
    constructor() {
        fieldsAfterMoving = null
        from = null
        to = null
        fromPiece = null
        toPiece = null
    }

    constructor(move: Move) {
        fieldsAfterMoving = mapArray(move.fieldsAfterMoving)
        from = PiecePositionXml(move.from)
        to = PiecePositionXml(move.to)
        fromPiece = PieceXml(move.fromPiece)
        toPiece = if (move.toPiece == null) null else PieceXml(move.toPiece)
    }

    /**
     * TODO
     */
    private fun mapArray(fields: Array<Array<Piece?>>): Array<Array<PieceXml?>> {
        return fields
                .map { row ->
                    row.map { piece ->
                        if (piece == null) null else PieceXml(piece)
                    }.toTypedArray()
                }.toTypedArray()
    }
}