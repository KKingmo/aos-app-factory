package kingmo.kkk.vocanote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kingmo.kkk.vocanote.databinding.ActivityMainBinding

/**
 * MainActivity는 사용자에게 단어 목록을 제공하며, 사용자의 인터랙션에 응답하는 액티비티입니다.
 * 이 액티비티는 WordAdapter를 사용하여 RecyclerView에 단어 목록을 표시합니다.
 */
class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding // 뷰 바인딩을 위한 인스턴스 변수
    private lateinit var wordAdapter: WordAdapter // 단어 목록을 관리하고 표시하기 위한 어댑터 인스턴스 변수

    /**
     * onCreate는 액티비티의 생명주기에서 인스턴스가 생성될 때 호출되는 메서드입니다.
     * 이 메서드에서는 뷰 바인딩을 초기화하고, RecyclerView를 설정하며, 추가 버튼에 클릭 리스너를 설정합니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 초기화된 뷰를 화면에 설정

        initRecyclerVIew() // RecyclerView를 초기화하는 메서드 호출
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent) // 추가 버튼 클릭 시 AddActivity로 이동
        }
    }

    /**
     * initRecyclerView는 RecyclerView의 초기 설정을 담당하는 메서드입니다.
     * 이 메서드에서는 더미 데이터를 생성하고, 어댑터를 초기화한 후, RecyclerView에 설정합니다.
     * 또한, 아이템 간의 구분선을 추가합니다.
     */
    private fun initRecyclerVIew() {
        val dummyList = mutableListOf<Word>(
            Word("weather", "날씨", "명사"),
            Word("honey", "꿀", "명사"),
            Word("run", "실행하다", "동사"),
        )

        wordAdapter = WordAdapter(dummyList, this) // 어댑터 인스턴스 생성 및 초기화
        binding.wordRecyclerView.apply {
            adapter = wordAdapter // RecyclerView에 어댑터 설정
            layoutManager = LinearLayoutManager(this@MainActivity) // 레이아웃 매니저 설정
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            ) // 아이템 간 구분선 추가
        }
    }

    /**
     * onClick 메서드는 단어 아이템이 클릭되었을 때 실행되는 콜백 메서드입니다.
     * 이 메서드에서는 클릭된 단어의 텍스트를 사용하여 토스트 메시지를 표시합니다.
     *
     * @param word 사용자에 의해 클릭된 단어 객체.
     */
    override fun onClick(word: Word) {
        Toast.makeText(this, "${word.text} 가 클릭 되었습니다.", Toast.LENGTH_SHORT)
            .show() // 클릭된 단어를 사용하여 토스트 메시지 표시
    }
}
