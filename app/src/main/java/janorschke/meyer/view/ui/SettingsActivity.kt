package janorschke.meyer.view.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivitySettingsBinding
import janorschke.meyer.enums.SettingKeys

private const val LOG_TAG = "SettingsActivity"

/**
 * Activity for the Game Settings
 */
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private var playerNameEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerNameEditText = binding.edittextSettingsPlayerName

        binding.buttonSettingsSave?.setOnClickListener { saveSettings() }
        binding.buttonSettingsCancel?.setOnClickListener { finish() }

        // Load settings and display them in the UI
        loadSettings()
    }

    /**
     * Loads the Settings from the SharedPreferences
     */
    private fun loadSettings() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SettingKeys.SETTINGS_SHARED_PREF_TAG.name, Context.MODE_PRIVATE)

        // Get the player name from the SharedPreferences and display it in the EditText
        val playerName: String? = sharedPreferences.getString(SettingKeys.SETTINGS_SAVED_PLAYER_NAME.name, "")
        playerNameEditText?.setText(playerName)
        Log.d(LOG_TAG, "Settings loaded")
    }

    /**
     * Saves the Settings via SharedPreferences and returns back to the calling Activity
     */
    private fun saveSettings() {
        getSharedPreferences(SettingKeys.SETTINGS_SHARED_PREF_TAG.name, Context.MODE_PRIVATE).edit().let { editor ->
            val newPlayerName: String = playerNameEditText?.text.toString()

            // Save the changes in the SharedPreferences
            editor.putString(SettingKeys.SETTINGS_SAVED_PLAYER_NAME.name, newPlayerName)
            editor.apply()
        }
        Toast.makeText(applicationContext, "Settings saved", Toast.LENGTH_SHORT).show()

        Log.d(LOG_TAG, "Settings saved")
        finish()
    }
}