package janorschke.meyer.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityMainBinding

private const val LOG_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAi?.setOnClickListener {
            Log.d(LOG_TAG, "Start AiActivtity")
            startActivity(Intent(this, AiActivity::class.java))
        }

        binding.buttonOnline?.setOnClickListener {
            Log.d(LOG_TAG, "Start SettingsActivity")
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}