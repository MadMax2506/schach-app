package janorschke.meyer.service.model.game

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

data class Player(val color: PieceColor, val textResource: Int, val aiLevel: AiLevel?)