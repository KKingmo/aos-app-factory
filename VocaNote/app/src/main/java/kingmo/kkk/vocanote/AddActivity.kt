package kingmo.kkk.vocanote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.Chip
import kingmo.kkk.vocanote.databinding.ActivityAddBinding

/**
 * AddActivity 클래스는 단어를 추가하거나 수정하는 화면을 나타냅니다.
 * 사용자는 이 화면에서 단어, 뜻, 그리고 단어의 종류를 입력하거나 선택할 수 있습니다.
 */
class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding // 뷰 바인딩을 위한 인스턴스 변수
    private var originWord: Word? = null // 수정할 단어의 원본 데이터
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater) // 뷰 바인딩 객체 초기화
        setContentView(binding.root) // 화면에 바인딩된 뷰 설정

        initViews() // UI 컴포넌트 초기화

        // '추가' 버튼 클릭 리스너 설정
        binding.addButton.setOnClickListener {
            if (originWord == null) add() else edit()
        }
    }

    /**
     * UI 컴포넌트를 초기화하고, 필요한 이벤트 리스너를 설정하는 메서드입니다.
     */
    private fun initViews() {
        // 단어의 종류에 해당하는 Chip들을 생성하고 ChipGroup에 추가
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }

        // 단어 입력 필드의 텍스트 변경 리스너 설정
        binding.textInputEditText.addTextChangedListener {
            it?.let { text ->
                binding.textTextInputLayout.error = when (text.length) {
                    0 -> "값을 입력해주세요"
                    1 -> "2자 이상을 입력해주세요"
                    else -> null
                }
            }
        }

        // 수정 모드인 경우, 원본 단어 데이터를 가져와서 UI에 설정
        @Suppress("DEPRECATION")
        originWord = intent.getParcelableExtra("originWord")
        originWord?.let { word ->
            binding.textInputEditText.setText(word.text)
            binding.meanTextInputEditText.setText(word.mean)
            val selectedChip =
                binding.typeChipGroup.children.firstOrNull { (it as Chip).text == word.type } as? Chip
            selectedChip?.isChecked = true
        }
    }

    /**
     * 주어진 텍스트를 가진 Chip을 생성하고 반환하는 메서드입니다.
     */
    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    /**
     * 새로운 단어를 데이터베이스에 추가하는 메서드입니다.
     */
    private fun add() {
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val word = Word(text, mean, type)

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.insert(word)
            runOnUiThread {
                Toast.makeText(this, "저장 완료했습니다.", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent().putExtra("isUpdated", true)
            setResult(RESULT_OK, intent)
            finish()
        }.start()
    }

    /**
     * 원본 단어를 수정하여 데이터베이스에 업데이트하는 메서드입니다.
     */
    private fun edit() {
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val editWord = originWord?.copy(text = text, mean = mean, type = type)

        Thread {
            editWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.update(word)
                val intent = Intent().putExtra("editWord", editWord)
                setResult(RESULT_OK, intent)
                runOnUiThread { Toast.makeText(this, "수정을 완료했습니다.", Toast.LENGTH_SHORT).show() }
                finish()
            }
        }.start()
    }

}
