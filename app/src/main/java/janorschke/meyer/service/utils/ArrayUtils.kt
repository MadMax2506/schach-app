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

    fun <T> deepEquals(array1: Array<Array<T>>, array2: Array<Array<T>>): Boolean {
        if (array1.size != array2.size || array1.firstOrNull()?.size != array2.firstOrNull()?.size) return false

        for (y in array1.indices) {
            for (x in array1.first().indices) {
                if (array1[y][x] != array2[y][x]) return false
            }
        }
        return true
    }
}