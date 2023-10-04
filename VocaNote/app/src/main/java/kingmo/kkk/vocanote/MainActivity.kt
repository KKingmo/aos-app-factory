package kingmo.kkk.vocanote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kingmo.kkk.vocanote.databinding.ActivityMainBinding

/**
 * MainActivity는 단어 목록을 표시하고, 단어의 추가, 수정, 삭제 기능을 제공하는 액티비티입니다.
 * 사용자는 단어를 클릭하여 선택하고, 선택된 단어에 대한 상세 정보를 확인하거나 편집할 수 있습니다.
 */
class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding // 뷰 바인딩 객체
    private lateinit var wordAdapter: WordAdapter // 단어 목록을 표시하기 위한 어댑터
    private var selectedWord: Word? = null // 현재 선택된 단어

    // 단어 추가 결과를 처리하기 위한 콜백 등록
    private val updateAddWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false

        if (result.resultCode == RESULT_OK && isUpdated) {
            updateAddWord()
        }
    }

    // 단어 편집 결과를 처리하기 위한 콜백 등록
    private val updateEditWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        @Suppress("DEPRECATION")
        val editWord = result.data?.getParcelableExtra<Word>("editWord")
        if (result.resultCode == RESULT_OK && editWord != null) {
            updateEditWord(editWord)
        }
    }

    /**
     * 액티비티가 생성될 때 호출되는 메서드입니다.
     * 뷰 바인딩 초기화, RecyclerView 설정, 버튼 클릭 리스너 설정 등의 작업을 수행합니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // 뷰 바인딩 초기화
        setContentView(binding.root) // 초기화된 뷰를 화면에 설정

        initRecyclerVIew() // RecyclerView를 초기화하는 메서드 호출
        binding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                updateAddWordResult.launch(it)
            }
        }

        binding.deleteImageView.setOnClickListener {
            delete()
        }
        binding.editImageView.setOnClickListener {
            edit()
        }
    }

    /**
     * RecyclerView를 초기화하고, 데이터베이스에서 단어 목록을 가져와 표시하는 메서드입니다.
     */
    private fun initRecyclerVIew() {
        wordAdapter = WordAdapter(mutableListOf(), this) // 어댑터 인스턴스 생성 및 초기화
        binding.wordRecyclerView.apply {
            adapter = wordAdapter // RecyclerView에 어댑터 설정
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        Thread {
            val list = AppDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.list.addAll(list)
            runOnUiThread { wordAdapter.notifyDataSetChanged() }
        }.start()
    }

    /**
     * 새로 추가된 단어를 RecyclerView에 반영하는 메서드입니다.
     */
    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()
                ?.let { word ->
                    wordAdapter.list.add(0, word)
                    runOnUiThread {
                        wordAdapter.notifyDataSetChanged()
                    }
                }
        }.start()
    }

    /**
     * 편집된 단어 정보를 RecyclerView에 반영하는 메서드입니다.
     * @param word 편집된 단어 객체.
     */
    private fun updateEditWord(word: Word) {
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list[index] = word
        runOnUiThread {
            selectedWord = word
            wordAdapter.notifyItemChanged(index)
            binding.textTextView.text = word.text
            binding.meanTextView.text = word.mean
        }
    }

    /**
     * 선택된 단어를 데이터베이스에서 삭제하고, RecyclerView에서 제거하는 메서드입니다.
     */
    private fun delete() {
        if (selectedWord == null) return

        Thread {
            selectedWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.delete(word)
                runOnUiThread {
                    wordAdapter.list.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.textTextView.text = ""
                    binding.meanTextView.text = ""
                    Toast.makeText(this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    /**
     * 선택된 단어를 편집하기 위해 AddActivity로 이동하는 메서드입니다.
     */
    private fun edit() {
        if (selectedWord == null) return

        val intent = Intent(this, AddActivity::class.java).putExtra("originWord", selectedWord)
        updateEditWordResult.launch(intent)
    }

    /**
     * 단어 아이템 클릭 시 호출되는 메서드입니다.
     * 클릭된 단어의 정보를 화면에 표시합니다.
     * @param word 클릭된 단어 객체.
     */
    override fun onClick(word: Word) {
        selectedWord = word
        binding.textTextView.text = word.text
        binding.meanTextView.text = word.mean
    }
}
