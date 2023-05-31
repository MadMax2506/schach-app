package janorschke.meyer.service.utils

/**
 * TODO
 */
object ArrayUtils {
    inline fun <reified T> copy(el: Array<Array<T>>) = el.map { it.copyOf() }.toTypedArray()
}