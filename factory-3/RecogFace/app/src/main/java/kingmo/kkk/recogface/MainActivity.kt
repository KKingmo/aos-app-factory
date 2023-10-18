package kingmo.kkk.recogface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kingmo.kkk.recogface.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            startDetectButton.setOnClickListener {
                it.isVisible = false
            }
        }
    }
}