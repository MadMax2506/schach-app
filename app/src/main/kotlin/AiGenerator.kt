import android.annotation.SuppressLint
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.repository.ai.AiLevelEasyRepository
import janorschke.meyer.service.repository.ai.AiLevelHardRepository
import janorschke.meyer.service.repository.ai.AiLevelMediumRepository
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.xml.AiEvaluationNodeXml
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureTimeMillis

/**
 * Generates for each color and for each ai level the evaluation tree
 */
@SuppressLint("NewApi")
fun main() {
    val dir = "./app/generatedObjects"

    // Recreate Directory
    val dirPaths = Paths.get(dir)
    println("Recreates dir=$dir for the generated objects\n")
    if (Files.exists(dirPaths)) {
        Files.list(dirPaths).forEach { file -> Files.delete(file) }
        Files.delete(dirPaths)
    }
    Files.createDirectory(dirPaths)

    PieceColor.values().forEach { color ->
        AiLevel.values().forEach { level ->
            // TODO Does not work for the strongest ai -> error with field validator
            if (level != AiLevel.CHRIS) {
                val name = "generated_ai_for_${color}_with_level_${level}.xml"

                println("Start with level=$level for color=$color")

                var node: AiEvaluationNodeXml
                val timeCalcTree = measureTimeMillis { node = AiEvaluationNodeXml(createRepository(color, level).evaluationTree.getRoot()) }
                println("Calculated tree for $level for $color in ${timeCalcTree}ms")

                val timeMarshal = measureTimeMillis { XMLSerialisation.evaluationNodeToString(node, dir, name) }
                println("Marshal $level for $color in ${timeMarshal}ms")

                println("Finished with level=$level for color=$color\n")
            }
        }
    }
}

/**
 * TODO
 */
private fun createRepository(color: PieceColor, level: AiLevel): AiRepository {
    return when (level) {
        AiLevel.KEVIN_OTTO -> AiLevelEasyRepository(color, Board(), History())
        AiLevel.MAX -> AiLevelMediumRepository(color, Board(), History())
        AiLevel.CHRIS -> AiLevelHardRepository(color, Board(), History())
    }
}