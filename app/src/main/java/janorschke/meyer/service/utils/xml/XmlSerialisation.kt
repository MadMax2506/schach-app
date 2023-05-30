package janorschke.meyer.service.utils.xml

import janorschke.meyer.service.model.game.ai.AiEvaluationNodeXml
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/107
 */
object XmlSerialisation {
    private val jc = JAXBContext.newInstance(AiEvaluationNodeXml::class.java)
    private val marshaller = jc.createMarshaller()

    init {
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    fun <T> toString(elem: T, dir: String, name: String) {
        marshaller.marshal(elem, File("${dir}/${name}"))
    }


}