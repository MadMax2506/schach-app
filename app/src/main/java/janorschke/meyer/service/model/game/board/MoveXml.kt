package janorschke.meyer.service.model.game.board

import janorschke.meyer.service.model.BaseXml
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.piece.PieceXml
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
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

    fun toMove(): Move {
        return Move(
                fieldsAfterMoving!!.map { row -> row.map { it?.toPiece() }.toTypedArray() }.toTypedArray(),
                from!!.toPiecePosition(),
                to!!.toPiecePosition(),
                fromPiece!!.toPiece(),
                toPiece!!.toPiece()
        )
    }

    /**
     * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
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