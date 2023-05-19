package janorschke.meyer.view.dialog

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.io.Serializable

/**
 * Dialog with custom specifications for the application
 */
abstract class BaseDialog : DialogFragment() {
    protected inline fun <reified T : Serializable> Bundle.optionalSerializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> (getSerializable(key, T::class.java))
        else -> @Suppress("DEPRECATION") getSerializable(key) as T
    }

    protected inline fun <reified T : Serializable> Bundle.requiredSerializable(key: String): T = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)!! as T
        else -> @Suppress("DEPRECATION") getSerializable(key)!! as T
    }
}