package janorschke.meyer.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityTimeModeBinding

class TimeModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeModeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}