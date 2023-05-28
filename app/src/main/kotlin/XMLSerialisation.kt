import android.annotation.SuppressLint
import janorschke.meyer.service.xml.AiEvaluationNodeXml
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * TODO
 */
object XMLSerialisation {
    private val jc = JAXBContext.newInstance(AiEvaluationNodeXml::class.java)
    private val marshaller: Marshaller = jc.createMarshaller()
    // private val unmarshaller: Unmarshaller = jc.createUnmarshaller()

    init {
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    @SuppressLint("NewApi")
    fun evaluationNodeToString(node: AiEvaluationNodeXml, dir: String, name: String) {
        marshaller.marshal(node, File("${dir}/${name}"))
    }

// TODO
//    fun xmlStringToEvaluationNode(xml: String?): AiEvaluationNode {
//        val r: Reader = StringReader(xml)
//        return unmarshaller.unmarshal(r) as EchoMessage
//    }
}