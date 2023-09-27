package kingmo.kkk.vocanote

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kingmo.kkk.vocanote.databinding.ItemWordBinding

/**
 * WordAdapter 클래스는 단어 데이터를 RecyclerView에 연결하여 UI에 표시하는 역할을 합니다.
 * 이 클래스는 Word 객체의 리스트를 받아, 각각의 아이템을 RecyclerView에 표시합니다.
 *
 * @property list Word 객체의 리스트. 이 리스트의 데이터는 RecyclerView에 표시됩니다.
 * @property itemClickListener 아이템이 클릭되었을 때 실행될 콜백 인터페이스.
 */
class WordAdapter(
    private val list: MutableList<Word>,
    private val itemClickListener: ItemClickListener? = null
) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    /**
     * onCreateViewHolder 메서드는 ViewHolder를 생성하고 초기화합니다.
     * 이 메서드는 RecyclerView가 새로운 아이템을 표시해야 할 때 호출되며,
     * 이 때 새로운 ViewHolder 인스턴스가 생성됩니다.
     *
     * @param parent 생성할 ViewHolder의 부모 뷰그룹.
     * @param viewType 생성할 뷰의 타입.
     * @return 초기화된 WordViewHolder 인스턴스.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    /**
     * onBindViewHolder 메서드는 ViewHolder에 데이터를 바인딩합니다.
     * 이 메서드는 RecyclerView가 특정 위치의 데이터를 리스트 아이템에 표시해야 할 때 호출됩니다.
     * 이 메서드에서는 Word 객체의 데이터를 ViewHolder의 뷰에 바인딩합니다.
     *
     * @param holder 데이터를 바인딩할 ViewHolder.
     * @param position 바인딩할 데이터의 위치.
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = list[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(word) }
    }

    /**
     * getItemCount 메서드는 RecyclerView에 표시될 아이템의 총 개수를 반환합니다.
     * 이 메서드는 RecyclerView가 아이템의 개수를 알아야 할 때 호출됩니다.
     *
     * @return 아이템의 총 개수.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * WordViewHolder 클래스는 단어 정보를 표시하는 뷰를 관리합니다.
     * 이 클래스는 ItemWordBinding을 통해 뷰에 데이터를 바인딩하며,
     * 각각의 뷰에 Word 객체의 데이터를 설정합니다.
     *
     * @property binding 뷰 바인딩 객체. 이 객체를 통해 뷰에 접근할 수 있습니다.
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
     * ItemClickListener 인터페이스는 아이템이 클릭되었을 때의 동작을 정의합니다.
     */
    interface ItemClickListener {
        fun onClick(word: Word)
    }
}