package janorschke.meyer.service.model.game.player

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

class AiPlayer(color: PieceColor, textResource: Int, val aiLevel: AiLevel) : Player(color, textResource)
