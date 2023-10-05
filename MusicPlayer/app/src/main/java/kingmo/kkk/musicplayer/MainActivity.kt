package kingmo.kkk.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kingmo.kkk.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {  }
        binding.pauseButton.setOnClickListener {  }
        binding.stopButton.setOnClickListener {  }
    }
}