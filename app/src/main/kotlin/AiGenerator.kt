import android.annotation.SuppressLint
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationNodeXml
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.repository.ai.AiLevelEasyRepository
import janorschke.meyer.service.repository.ai.AiLevelHardRepository
import janorschke.meyer.service.repository.ai.AiLevelMediumRepository
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.utils.xml.XmlPathGenerator
import janorschke.meyer.service.utils.xml.XmlPathGenerator.DIR
import janorschke.meyer.service.utils.xml.XmlSerialisation
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureTimeMillis

/**
 * Generates for each color and for each ai level the evaluation tree
 */
@SuppressLint("NewApi")
fun main() {
    // Recreate Directory
    val dirPaths = Paths.get(DIR)
    println("Recreates dir=$DIR for the generated objects\n")
    if (Files.exists(dirPaths)) {
        Files.list(dirPaths).forEach { file -> Files.delete(file) }
        Files.delete(dirPaths)
    }
    Files.createDirectory(dirPaths)

    val completeTime = measureTimeMillis {
        AiLevel.values().forEach { level ->
            PieceColor.values().forEach { color ->
                println("Start with level=$level for color=$color")

                println("Calculate tree for $level for ${color}....")
                var node: AiEvaluationNodeXml
                val timeCalcTree = measureTimeMillis {
                    node = AiEvaluationNodeXml(createRepository(color, level).evaluationTree.getRoot())
                }
                println("Calculated tree for $level for $color in ${timeCalcTree}ms")

                println("Marshal $level for ${color}....")
                val timeMarshal = measureTimeMillis {
                    XmlSerialisation.toString(node, DIR, XmlPathGenerator.getAi(color, level))
                }
                println("Marshal $level for $color in ${timeMarshal}ms")

                println("Finished with level=$level for color=$color\n")
            }
        }
    }

    println("Finished after ${completeTime}ms")
}


/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
 */
private fun createRepository(color: PieceColor, level: AiLevel): AiRepository {
    return when (level) {
        AiLevel.KEVIN_OTTO -> AiLevelEasyRepository(color, Board(), false)
        AiLevel.MAX -> AiLevelMediumRepository(color, Board(), false)
        AiLevel.CHRIS -> AiLevelHardRepository(color, Board(), false)
    }
}