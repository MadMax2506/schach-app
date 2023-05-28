package janorschke.meyer.service.xml

import janorschke.meyer.service.model.game.ai.AiEvaluationNode
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class AiEvaluationNodeXml : BaseXml {
    var move: MoveXml?
    var history: HistoryXml?
    var valency: Int?

    @XmlElementWrapper(name = "children")
    @XmlElement(name = "child")
    var children: ArrayList<AiEvaluationNodeXml>?

    // Needed for XML serialization
    constructor() {
        move = null
        history = null
        valency = null
        children = null
    }

    constructor(node: AiEvaluationNode) {
        move = if (node.move == null) null else MoveXml(node.move)
        history = HistoryXml(node.history)
        valency = node.valency
        children = mapChildren(node.getChildren())
    }

    private fun mapChildren(children: MutableList<AiEvaluationNode>?): ArrayList<AiEvaluationNodeXml>? {
        return if (children == null) null
        else listToArrayList(children.map { child -> AiEvaluationNodeXml(child) }.toMutableList())
    }
}