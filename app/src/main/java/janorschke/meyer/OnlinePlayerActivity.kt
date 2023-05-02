package janorschke.meyer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityOnlineplayerBinding

class OnlinePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineplayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEinladungVersenden?.setOnClickListener {
            // TODO
        }
    }
}
