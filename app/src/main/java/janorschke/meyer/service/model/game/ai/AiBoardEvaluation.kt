package janorschke.meyer.service.model.game.ai

import janorschke.meyer.service.model.game.board.Move

data class AiBoardEvaluation(val move: Move, val valency: Int)