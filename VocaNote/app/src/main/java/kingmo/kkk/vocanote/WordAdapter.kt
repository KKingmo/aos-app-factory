package kingmo.kkk.vocanote

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kingmo.kkk.vocanote.databinding.ItemWordBinding

/**
 * WordAdapter 클래스는 단어 목록을 RecyclerView에 표시하기 위한 어댑터입니다.
 * 이 클래스는 Word 객체의 리스트를 받아 RecyclerView에 바인딩하며, 각 아이템 클릭 시의 동작도 처리합니다.
 *
 * @param list 표시할 단어 정보를 담은 Word 객체의 리스트.
 * @param itemClickListener 아이템 클릭 시 호출될 콜백 인터페이스.
 */
class WordAdapter(
    val list: MutableList<Word>,
    private val itemClickListener: ItemClickListener? = null
) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    /**
     * ViewHolder를 생성하고 뷰를 바인딩하는 메서드.
     * @param parent ViewHolder의 부모 뷰 그룹.
     * @param viewType 뷰의 타입.
     * @return 생성된 WordViewHolder 객체.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    /**
     * 주어진 위치의 데이터를 ViewHolder의 뷰에 바인딩하는 메서드.
     * @param holder 바인딩할 ViewHolder 객체.
     * @param position 바인딩할 데이터의 리스트 내 위치.
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = list[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(word) }
    }

    /**
     * RecyclerView에 표시될 아이템의 총 개수를 반환하는 메서드.
     * @return 아이템의 총 개수.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * WordViewHolder 클래스는 단어 정보를 표시하는 뷰를 관리합니다.
     * @param binding 바인딩 객체.
     */
    class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                textTextView.text = word.text
                meanTextView.text = word.mean
                typeChip.text = word.type
            }
        }
    }

    /**
     * ItemClickListener 인터페이스는 아이템 클릭 이벤트를 처리하기 위한 콜백 메서드를 정의합니다.
     */
    interface ItemClickListener {
        fun onClick(word: Word)
    }
}