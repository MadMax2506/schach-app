package janorschke.meyer.service.model.game

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import java.io.Serializable

data class Player(val color: PieceColor, val textResource: Int, val aiLevel: AiLevel?) : Serializable