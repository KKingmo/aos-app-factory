package kingmo.kkk.stopwatch

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kingmo.kkk.stopwatch.databinding.ActivityMainBinding
import kingmo.kkk.stopwatch.databinding.DialogCountdownSettingBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countdownSecond = 10
    private var currentCountdownDeciSecond = countdownSecond * 10
    private var currentDeciSecond = 0
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 카운트다운 클릭 시 카운트다운 설정 다이얼로그 표시
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
        initCountdownViews()
    }

    /**
     * 카운트다운 UI 초기화
     */
    private fun initCountdownViews() {
        binding.countdownTextView.text = String.format("%02d", countdownSecond)
        binding.countdownProgressBar.progress = 100
    }


    /**
     * `timer { }`를 통해 작업자 스레드에서 작업을 실행합니다.
     * 작업하고 Handler를 통해 UI에 반영하기 위해 `runOnUiThread`, `binding.root.post`같은 놈들을 사용 합니다.
     */
    private fun start() {
        timer = timer(initialDelay = 0, period = 100) {
            if (currentCountdownDeciSecond == 0) {
                currentDeciSecond += 1

                val minutes = currentDeciSecond.div(10) / 60
                val seconds = currentDeciSecond.div(10) % 60
                val deciSeconds = currentDeciSecond % 10

                runOnUiThread {
                    binding.timeTextView.text = String.format("%02d:%02d", minutes, seconds)
                    binding.tickTextView.text = deciSeconds.toString()

                    binding.countdownGroup.isVisible = false
                }
            } else {
                currentCountdownDeciSecond -= 1
                val seconds = currentCountdownDeciSecond / 10
                val progress = (currentCountdownDeciSecond / (countdownSecond * 10f)) * 100

                binding.root.post {
                    binding.countdownTextView.text = String.format("%02d", seconds)
                    binding.countdownProgressBar.progress = progress.toInt()
                }
            }
            if (currentDeciSecond == 0 && currentCountdownDeciSecond < 31 && currentCountdownDeciSecond % 10 == 0) {
                val tonType =
                    if (currentCountdownDeciSecond == 0) ToneGenerator.TONE_CDMA_HIGH_L else ToneGenerator.TONE_CDMA_ANSWER
                ToneGenerator(
                    AudioManager.STREAM_ALARM,
                    ToneGenerator.MAX_VOLUME
                ).startTone(tonType, 100)
            }
        }
    }

    /**
     * 스톱워치 중지
     */
    private fun stop() {
        binding.startButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.pauseButton.isVisible = false
        binding.lapButton.isVisible = false

        currentDeciSecond = 0
        binding.timeTextView.text = "00:00"
        binding.tickTextView.text = "0"

        binding.countdownGroup.isVisible = true
        initCountdownViews()
        binding.lapContainerLinearLayout.removeAllViews()
    }

    /**
     * 스톱워치 일시정지
     * 경과시간은 currentDeciSecond에 저장됩니다.
     */
    private fun pause() {
        timer?.cancel()
        timer = null
    }

    /**
     * lap Time 기록
     */
    private fun lap() {
        if (currentDeciSecond == 0) return
        val container = binding.lapContainerLinearLayout
        TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10
            text = "${container.childCount.inc()}. " + String.format(
                "%02d:%02d %01d",
                minutes, seconds, deciSeconds
            )
            setPadding(30, 30, 30, 30)
        }.let { labTextView ->
            container.addView(labTextView, 0)
        }
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
                currentCountdownDeciSecond = countdownSecond * 10
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

/**
 * 안드로이드 스레드 이해 :
 * - 안드로이드 앱은 하나 이상의 스레드로 구성되고, 스레드는 프로세스 내에서 실행되는 독립적인 실행흐름입니다.
 * - 각 스레드는 자체 메모리 스택과 실행 스택을 갖습니다.
 *
 * UI 스레드 :
 * - 앱의 UI를 업데이트에 사용되는 스레드입니다.
 * - Android 시스템에 의해 생성되며, 일반적으로 메인 스레드라고 불립니다.
 * - UI 스레드에서만 UI를 조작해야하며, UI 스레드를 차단하면 UI가 응답하지 않습니다.
 * - UI 스레드를 차단하는 작업 :
 *   - 네트워크 요청, 데이터베이스 쿼리, 파일 I/O
 *
 * 작업자 스레드 :
 * - UI 스레드와 별개로 실행되는 스레드로 CPU 많이쓰는 작업이나 UI 스레드를 차단할 수 있는 작업을 수행하는 데 사용됩니다.
 * - 작업자 스레드에서 UI 스레드로 UI 업데이트 하려면 Handler를 사용해야 합니다.
 *
 * UI 스레드를 차단하지 않도록 해야하는 이유 :
 * - UI 스레드를 차단하면 UI가 응답하지 않아 사용자는 앱이 고장났다고 느끼게 됩니다.
 * - UI 스레드가 5초 이상 차단 되었을 때 ANR(Application Not Responding) 오류가 발생하여 앱이 강제 종료될 수 있습니다.
 *
 * UI 스레드를 차단하지 않는 방법 :
 * - UI는 UI 스레드에서만 조작합니다.
 * - 작업자 스레드에서 UI 스레드로 UI를 업데이트 하려면 Handler를 사용합시다.
 *
 * Handler 사용 방법 :
 * - Handler를 사용하여 작업자 스레드에서 UI 스레드로 메시지를 보내거나, 반대로 UI 스레드에서 작업자 스레드로 작업을 요청할 수 있습니다.
 * - Handler는 메시지 큐를 사용하여 UI 스레드와 작업합니다.
 * - 메시지 큐에 작업이 등록되면 Looper라는 놈이 메시지 큐의 작업들을 가져와 UI 스레드에서 처리되도록 합니다.
 *
 * 결론 :
 * UI 조작은 UI 스레드에서만 합시다. UI 스레드 외부에서 작업하고 UI에 반영할 때는 Handler 사용 합시다.
 */