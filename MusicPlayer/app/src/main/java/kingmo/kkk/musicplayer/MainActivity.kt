package kingmo.kkk.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kingmo.kkk.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener { mediaPlayerPlay() }
        binding.pauseButton.setOnClickListener { mediaPlayerPause() }
        binding.stopButton.setOnClickListener { mediaPlayerStop() }
    }

    private fun mediaPlayerPlay() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgm).apply {
                isLooping = true
            }
        }
        mediaPlayer?.start()
    }

    private fun mediaPlayerPause() {
        mediaPlayer?.pause()
    }

    private fun mediaPlayerStop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}