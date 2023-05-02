package janorschke.meyer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}