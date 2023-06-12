package janorschke.meyer.service.utils

/**
 * Provides functionality for Arrays
 */
object ArrayUtils {

    /**
     * Copies a 2D Array
     * @param el Array that shall be copied
     * @return Array-Copy
     */
    inline fun <reified T> deepCopy(el: Array<Array<T>>) = el.map { it.copyOf() }.toTypedArray()
}