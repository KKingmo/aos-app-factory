package kingmo.kkk.emergencyinformation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import kingmo.kkk.emergencyinformation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 'goInputActivityButton' 클릭 시, EditActivity로 이동합니다.
         * 이를 위해 명시적 Intent를 생성하고 startActivity를 호출하여 EditActivity를 시작합니다.
         */
        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java) // 명시적 Intent 생성
            startActivity(intent) // EditActivity 시작
        }

        /**
         * 'deleteButton' 클릭 시, 'deleteData' 함수를 호출하여 저장된 사용자 정보를 삭제합니다.
         */
        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        /**
         * 'emergencyContactLayer' 클릭 시, 사용자의 비상 연락처로 전화를 걸 수 있도록 합니다.
         * 이를 위해 암시적 Intent를 생성하고, 'Intent.ACTION_VIEW' 액션을 사용합니다.
         * 'data' 프로퍼티에는 전화번호를 'tel:' 스키마와 함께 URI로 설정합니다.
         */
        binding.emergencyContactLayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber =
                    binding.emergencyContactValueTextView.text.toString().replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    /**
     * 'onResume' 메서드는 액티비티가 사용자와 상호작용하기 직전에 호출됩니다.
     * 이 메서드에서 'getDataUiUpdate' 함수를 호출하여 UI를 최신 상태로 업데이트합니다.
     * 이는 사용자가 EditActivity에서 데이터를 변경한 후 다시 MainActivity로 돌아왔을 때 변경사항을 반영하기 위함입니다.
     */
    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }

    /**
     * 'getDataUiUpdate' 함수는 Shared Preferences로부터 사용자 정보를 읽어와 UI를 업데이트합니다.
     * 각 TextView는 해당하는 데이터가 없을 경우 "미정"이라는 기본값을 표시합니다.
     * 또한, 경고 메시지가 있을 경우에만 경고 메시지 관련 TextView를 표시합니다.
     */
    private fun getDataUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
            val warning = getString(WARNING, "")

            binding.warningTextView.isVisible = warning.isNullOrEmpty().not()
            binding.warningValueTextView.isVisible = warning.isNullOrEmpty().not()

            if (!warning.isNullOrEmpty()) {
                binding.warningValueTextView.text = warning
            }
        }
    }

    /**
     * 'deleteData' 함수는 Shared Preferences의 모든 데이터를 삭제합니다.
     * 이 함수는 'deleteButton'이 클릭될 때 호출되며, 데이터 삭제 후에는 'getDataUiUpdate' 함수를 호출하여 UI를 업데이트합니다.
     * 마지막으로, 데이터가 성공적으로 삭제되면 사용자에게 토스트 메시지로 알립니다.
     */
    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear() // 모든 데이터 삭제
            apply() // 변경사항 적용
            getDataUiUpdate() // UI 업데이트
        }
        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}