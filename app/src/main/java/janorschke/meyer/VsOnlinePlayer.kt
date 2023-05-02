package janorschke.meyer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityVsonlineplayerBinding

class VsOnlinePlayer : AppCompatActivity() {
    private lateinit var binding: ActivityVsonlineplayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVsonlineplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
