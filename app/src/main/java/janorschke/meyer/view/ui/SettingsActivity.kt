package janorschke.meyer.view.ui

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.R
import janorschke.meyer.databinding.ActivitySettingsBinding
import janorschke.meyer.enums.SettingKeys
import janorschke.meyer.service.utils.SettingsManager

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

        binding.buttonSettingsSave?.setOnClickListener { saveAndExit() }
        binding.buttonSettingsCancel?.setOnClickListener { finish() }

        loadSettings()
    }

    /**
     * Gets the Settings from the SettingsManager and displays them in the UI
     */
    private fun loadSettings() {
        val playerName: String? = SettingsManager.loadSettings(applicationContext, SettingKeys.SETTINGS_SAVED_PLAYER_NAME.name)
        playerNameEditText?.setText(playerName)

        Log.d(LOG_TAG, "Settings loaded")
    }

    /**
     * Saves the Settings through the SettingsManager and returns back to the calling Activity
     */
    private fun saveAndExit() {
        val newPlayerName: String = playerNameEditText?.text.toString().trim()
        SettingsManager.saveSettings(applicationContext, SettingKeys.SETTINGS_SAVED_PLAYER_NAME.name, newPlayerName)

        Toast.makeText(applicationContext, getString(R.string.saved_settings), Toast.LENGTH_SHORT).show()

        Log.d(LOG_TAG, "Settings saved")
        finish()
    }
}