package kingmo.kkk.vocanote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.Chip
import kingmo.kkk.vocanote.databinding.ActivityAddBinding

/**
 * AddActivity는 사용자가 새로운 단어를 추가할 수 있는 화면을 제공하는 액티비티입니다.
 * 이 액티비티에서는 단어의 종류(명사, 동사 등)를 선택할 수 있는 Chip들을 동적으로 생성하고 관리합니다.
 */
class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding // 이 액티비티의 뷰를 관리하는 뷰 바인딩 인스턴스

    /**
     * onCreate는 액티비티가 생성될 때 시스템에 의해 호출되는 메서드입니다.
     * 이 메서드에서는 뷰 바인딩을 통해 UI 컴포넌트를 초기화하고, 필요한 뷰를 설정합니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater) // 뷰 바인딩 객체를 초기화
        setContentView(binding.root) // 초기화된 바인딩 객체를 이용해 뷰를 설정

        initViews() // UI 컴포넌트 초기화 메서드 호출
    }

    /**
     * initViews는 액티비티의 UI 컴포넌트를 초기화하는 메서드입니다.
     * 이 메서드에서는 단어의 종류를 나타내는 Chip들을 생성하여 ChipGroup에 추가합니다.
     */
    private fun initViews() {
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
        binding.typeChipGroup.apply {
            types.forEach { text -> // 단어의 종류 리스트를 순회하며
                addView(createChip(text)) // 각 종류에 해당하는 Chip을 생성하고 ChipGroup에 추가
            }
        }
    }

    /**
     * createChip는 주어진 텍스트를 바탕으로 새로운 Chip 인스턴스를 생성하고 반환하는 메서드입니다.
     * 생성된 Chip은 사용자의 선택과 클릭에 반응할 수 있도록 설정됩니다.
     *
     * @param text Chip에 표시될 단어의 종류를 나타내는 텍스트.
     * @return 텍스트가 설정된 새로운 Chip 인스턴스.
     */
    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text) // 생성된 Chip에 텍스트 설정
            isCheckable = true // Chip을 선택 가능한 상태로 설정
            isClickable = true // Chip을 클릭 가능한 상태로 설정
        }
    }
}
