import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

/**
 * Generates for each color and for each ai level the evaluation tree
 */
fun main() {
    PieceColor.values().forEach { color ->
        AiLevel.values().forEach { level ->
            // TODO Does not work for the strongest ai -> error with field validator
            if (level != AiLevel.CHRIS) AiGeneratorThread(color, level).start()
        }
    }
}