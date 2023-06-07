package janorschke.meyer.service.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Provides functionality to save and load Settings via SharedPreferences
 */
object SettingsManager {
    private const val SETTINGS_SHARED_PREF_TAG = "SettingsSharedPrefs"

    /**
     * Loads the settings from the SharedPreferences.
     *
     * @param context of the application.
     * @param key to retrieve the setting value.
     * @return value associated with the specified key, or null if not found.
     */
    fun loadSettings(context: Context, key: String): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREF_TAG, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    /**
     * Saves the settings via SharedPreferences.
     *
     * @param context of the application.
     * @param key to save the setting value.
     * @param newValue to be saved.
     */
    fun saveSettings(context: Context, key: String, newValue: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREF_TAG, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(key, newValue)
            apply()
        }
    }
}
