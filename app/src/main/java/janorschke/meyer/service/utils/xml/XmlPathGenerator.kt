package janorschke.meyer.service.utils.xml

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

object XmlPathGenerator {
    const val DIR = "./app/generatedObjects"

    fun getAi(color: PieceColor, level: AiLevel) = "generated_ai_for_${color}_with_level_${level}.xml"
}