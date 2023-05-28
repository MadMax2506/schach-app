import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.repository.ai.AiLevelEasyRepository
import janorschke.meyer.service.repository.ai.AiLevelHardRepository
import janorschke.meyer.service.repository.ai.AiLevelMediumRepository
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.xml.AiEvaluationNodeXml
import kotlin.system.measureTimeMillis

class AiGeneratorThread(val color: PieceColor, val level: AiLevel) : Thread() {
    override fun run() {
        val name = "generated_ai_for_${color}_with_level_${level}.xml"

        var node: AiEvaluationNodeXml
        val timeCalcTree = measureTimeMillis { node = AiEvaluationNodeXml(createRepository(color, level).evaluationTree.getRoot()) }
        println("Calculate tree for $level for $color in ${timeCalcTree}ms")

        val timeMarshal = measureTimeMillis { XMLSerialisation.evaluationNodeToString(node, name) }
        println("Marshal $level for $color in ${timeMarshal}ms")

        println("Finished with level=$level for color=$color")
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
}