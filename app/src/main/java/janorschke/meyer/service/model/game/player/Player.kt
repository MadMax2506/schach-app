package janorschke.meyer.service.model.game.player

import janorschke.meyer.enums.PieceColor
import java.io.Serializable

open class Player(val color: PieceColor, val name: String, var time: Long?) : Serializable {
    var requiredTime: Long
        get() = time!!
        set(time) {
            this.time = time
        }
}