package janorschke.meyer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityVsaiBinding

class VsAiActivtity : AppCompatActivity() {
    private lateinit var binding: ActivityVsaiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVsaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
