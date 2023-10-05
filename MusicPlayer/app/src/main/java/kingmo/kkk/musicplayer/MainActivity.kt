package kingmo.kkk.musicplayer

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kingmo.kkk.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener { mediaPlayerPlay() }
        binding.pauseButton.setOnClickListener { mediaPlayerPause() }
        binding.stopButton.setOnClickListener { mediaPlayerStop() }

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        val permission = "android.permission.POST_NOTIFICATIONS"

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE)
        }
    }

    companion object {
        const val REQUEST_CODE = 101
    }

    private fun mediaPlayerPlay() {
        val intent =
            Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PLAY }
        startService(intent)
    }

    private fun mediaPlayerPause() {
        val intent =
            Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE }
        startService(intent)
    }

    private fun mediaPlayerStop() {
        val intent =
            Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP }
        startService(intent)
    }

    override fun onDestroy() {
        stopService(Intent(this, MediaPlayerService::class.java))
        super.onDestroy()
    }
}