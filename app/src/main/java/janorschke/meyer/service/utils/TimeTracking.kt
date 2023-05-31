package janorschke.meyer.service.utils

import android.util.Log
import kotlin.system.measureTimeMillis

object TimeTracking {
    /**
     * @param logTag of the calling class
     * @param methodName of the calling method
     * @param function which will be run
     * @return function result
     */
    fun <T> log(logTag: String, methodName: String, function: () -> T): T {
        var value: T
        val time = measureTimeMillis { value = function() }

        Log.d(logTag, "$methodName needs ${time}ms to run.")
        return value
    }
}