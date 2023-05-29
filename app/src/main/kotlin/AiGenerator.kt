import android.annotation.SuppressLint
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.utils.XMLSerialisation
import janorschke.meyer.service.model.game.ai.AiEvaluationNodeXml
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

    val completeTime = measureTimeMillis {
        PieceColor.values().forEach { color ->
            AiLevel.values().forEach { level ->
                val name = "generated_ai_for_${color}_with_level_${level}.xml"

                println("Start with level=$level for color=$color")

                println("Calculate tree for $level for $color")
                var node: AiEvaluationNodeXml
                val timeCalcTree = measureTimeMillis {
                    node = AiEvaluationNodeXml(AiRepository(color, Board(), level.deepness + 2).evaluationTree.getRoot())
                }
                println("Calculated tree for $level for $color in ${timeCalcTree}ms")

                println("Marshal $level for $color")
                val timeMarshal = measureTimeMillis { XMLSerialisation.evaluationNodeToString(node, dir, name) }
                println("Marshal $level for $color in ${timeMarshal}ms")

                println("Finished with level=$level for color=$color\n")
            }
        }
    }

    println("Finished after ${completeTime}ms")
}