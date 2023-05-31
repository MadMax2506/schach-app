package janorschke.meyer.enums

import janorschke.meyer.R

enum class AiLevel(val resourceId: Int, val depth: Int) {
    KEVIN_OTTO(R.string.ai_kevin_otto, 0),
    MAX(R.string.ai_max, 2),
    CHRIS(R.string.ai_chris, 4)
}