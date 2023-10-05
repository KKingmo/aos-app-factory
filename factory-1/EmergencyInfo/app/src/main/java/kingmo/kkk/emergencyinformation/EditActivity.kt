package kingmo.kkk.emergencyinformation

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import kingmo.kkk.emergencyinformation.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * 'binding.bloodTypeSpinner.adapter'를 통해 'bloodTypeSpinner'에 어댑터를 설정합니다.
         * ArrayAdapter.createFromResource를 사용하여 blood_types 배열을 가져와 어댑터를 생성하며,
         * 이를 통해 사용자에게 혈액형을 선택할 수 있는 드롭다운 목록을 제공합니다.
         */
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.blood_types, android.R.layout.simple_list_item_1
        )

        /**
         * 'birthdateLayer' 클릭 시, 사용자에게 날짜를 선택할 수 있는 DatePickerDialog를 표시합니다.
         * 사용자가 날짜를 선택하면, 선택된 날짜가 'birthdateValueTextView'에 표시됩니다.
         */
        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthdateValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this, listener, 2000, 1, 1
            ).show()
        }

        /**
         * 'warningCheckBox'의 체크 상태에 따라 'warningEditText'의 가시성을 변경합니다.
         */
        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.warningEditText.isVisible = isChecked
        }

        binding.warningEditText.isVisible = binding.warningCheckBox.isChecked

        /**
         * 'saveButton' 클릭 시, 사용자의 정보를 저장하고 액티비티를 종료합니다.
         */
        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    /**
     * 'saveData' 함수는 사용자의 정보를 Shared Preferences에 저장합니다.
     * 이 함수는 사용자의 이름, 혈액형, 비상 연락처, 생년월일, 경고 메시지를 저장합니다.
     * 저장이 완료되면, 사용자에게 토스트 메시지로 저장 완료를 알립니다.
     */
    private fun saveData() {
        with(getSharedPreferences("userInformation", Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyContactEditText.text.toString())
            putString(BIRTHDATE, binding.birthdateTextView.text.toString())
            putString(WARNING, getWarning())
            apply() // apply는 commit과 달리 비동기로 처리하기 때문에 사용자의 동작을 막지 않습니다.
        }

        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show() // 알림 토스트
    }

    /**
     * 'getBloodType' 함수는 선택된 혈액형을 반환합니다.
     * 이 함수는 'bloodTypeSpinner'에서 선택된 항목과 'bloodTypePlus' 체크박스의 상태를 조합하여 결과를 생성합니다.
     */
    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if (binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    /**
     * 'getWarning' 함수는 'warningCheckBox'가 체크되어 있을 경우 'warningEditText'의 텍스트를 반환합니다.
     * 체크되어 있지 않은 경우, 빈 문자열을 반환합니다.
     */
    private fun getWarning(): String {
        return if (binding.warningCheckBox.isChecked) binding.warningEditText.text.toString() else ""
    }

}