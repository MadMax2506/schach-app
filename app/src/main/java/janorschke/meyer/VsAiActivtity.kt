package janorschke.meyer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityVsaiBinding

class VsAiActivtity : AppCompatActivity() {
    private lateinit var binding: ActivityVsaiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVsaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAiKevinOtto?.setOnClickListener {
            // TODO AI-Einstellungen mitnehmen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent, savedInstanceState)
        }
        binding.buttonAiMax?.setOnClickListener {
            // TODO AI-Einstellungen mitnehmen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent, savedInstanceState)
        }
        binding.buttonAiChris?.setOnClickListener {
            // TODO AI-Einstellungen mitnehmen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent, savedInstanceState)
        }
    }
}
