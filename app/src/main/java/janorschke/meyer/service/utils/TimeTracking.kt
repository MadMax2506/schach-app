package janorschke.meyer.service.utils

import android.util.Log

object TimeTracking {
    /**
     * @param logTag of the calling class
     * @param methodName of the calling method
     * @param function which will be run
     * @return function resul
     */
    fun <T> log(logTag: String, methodName: String, function: () -> T): T {
        val start = System.currentTimeMillis()
        val value = function()
        val end = System.currentTimeMillis()

        Log.d(logTag, "$methodName needs ${end - start}ms to run.")
        return value
    }
}