package janorschke.meyer.view.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivitySettingsBinding

private const val LOG_TAG = "SettingsActivity"

// TODO Keys sind globale Variablen (ekelhaft)
const val SETTINGS_PREF_TAG = "AppSettings"
const val SETTINGS_SAVED_PLAYERNAME = "PlayerName"

/**
 * TODO JavaDoc
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
     * TODO JavaDoc
     */
    private fun loadSettings() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SETTINGS_PREF_TAG, Context.MODE_PRIVATE)

        // Get the player name from the SharedPreferences and display it in the EditText
        val playerName: String? = sharedPreferences.getString(SETTINGS_SAVED_PLAYERNAME, "")
        playerNameEditText?.setText(playerName)
        Log.d(LOG_TAG, "Settings loaded")
    }

    /**
     * TODO JavaDoc
     */
    private fun saveSettings() {
        getSharedPreferences(SETTINGS_PREF_TAG, Context.MODE_PRIVATE).edit().let { editor ->
            val newPlayerName: String = playerNameEditText?.text.toString()

            // Save the changes in the SharedPreferences
            editor.putString(SETTINGS_SAVED_PLAYERNAME, newPlayerName)
            editor.apply()
        }
        Toast.makeText(applicationContext, "Settings saved", Toast.LENGTH_SHORT).show()

        Log.d(LOG_TAG, "Settings saved")
        finish()
    }
}