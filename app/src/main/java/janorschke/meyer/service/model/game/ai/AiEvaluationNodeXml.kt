package janorschke.meyer.service.model.game.ai

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.BaseXml
import janorschke.meyer.service.model.game.board.MoveXml
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class AiEvaluationNodeXml : BaseXml {
    var move: MoveXml?
    var valency: Int?

    @XmlElementWrapper(name = "children")
    @XmlElement(name = "child")
    var children: ArrayList<AiEvaluationNodeXml>?

    // Needed for XML serialization
    constructor() {
        move = null
        valency = null
        children = null
    }

    constructor(node: AiEvaluationNode) {
        move = if (node.move == null) null else MoveXml(node.move)
        valency = node.valency
        children = mapChildren(node.getChildren())
    }

    fun toNode(color: PieceColor, parent: AiEvaluationNode? = null): AiEvaluationNode {
        val mappedNode = if (parent == null) AiEvaluationNode(color)
        else AiEvaluationNode(color, move!!.toMove(), parent)

        val mappedChildren = children?.map { it.toNode(color, mappedNode) }?.toMutableList()
        if (mappedChildren != null) mappedNode.setChildren(mappedChildren)

        return mappedNode
    }

    private fun mapChildren(children: MutableList<AiEvaluationNode>?): ArrayList<AiEvaluationNodeXml>? {
        return if (children == null) null
        else listToArrayList(children.map { child -> AiEvaluationNodeXml(child) }.toMutableList())
    }
}