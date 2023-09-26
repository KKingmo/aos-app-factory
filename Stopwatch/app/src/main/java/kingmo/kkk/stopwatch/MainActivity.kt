package kingmo.kkk.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kingmo.kkk.stopwatch.databinding.ActivityMainBinding
import kingmo.kkk.stopwatch.databinding.DialogCountdownSettingBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countdownSecond = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 카운트다운 텍스트 뷰가 클릭될 때 카운트다운 설정 다이얼로그 표시
         */
        binding.countdownTextView.setOnClickListener {
            showCountdownSettingDialog()
        }

        /**
         * 시작 버튼 클릭 시 스톱워치 시작
         */
        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.stopButton.isVisible = false
            binding.pauseButton.isVisible = true
            binding.lapButton.isVisible = true
        }

        /**
         * 중지 버튼 클릭 시 종료 확인 다이얼로그 표시
         */
        binding.stopButton.setOnClickListener {
            showAlertDialog()
        }

        /**
         * 일시정지 버튼 클릭 시 일시정지
         */
        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.isVisible = true
            binding.stopButton.isVisible = true
            binding.pauseButton.isVisible = false
            binding.lapButton.isVisible = false
        }

        /**
         * 랩 버튼 클릭 시 현재시간 기록
         */
        binding.lapButton.setOnClickListener {
            lap()
        }
    }

    private fun start() {

    }

    /**
     * 스톱워치 중지
     */
    private fun stop() {
        binding.startButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.pauseButton.isVisible = false
        binding.lapButton.isVisible = false
    }

    private fun pause() {

    }

    private fun lap() {

    }

    /**
     * 카운트 다운 설정 다이얼로그 표시
     */
    private fun showCountdownSettingDialog() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(dialogBinding.countdownSecondPicker) {
                maxValue = 20
                minValue = 0
                value = countdownSecond
            }
            setTitle("카운트다운 설정")
            setView(dialogBinding.root)
            setPositiveButton("확인") { _, _ ->
                countdownSecond = dialogBinding.countdownSecondPicker.value
                binding.countdownTextView.text = String.format("%02d", countdownSecond)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    /**
     * 종료 확인 다이얼로그 표시
     */
    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }
}