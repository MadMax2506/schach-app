package janorschke.meyer.service.model.game.player

import janorschke.meyer.enums.PieceColor
import java.io.Serializable

open class Player(val color: PieceColor, val textResource: Int) : Serializable