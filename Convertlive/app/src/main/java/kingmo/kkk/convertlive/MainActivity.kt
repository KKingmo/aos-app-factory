package kingmo.kkk.convertlive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kingmo.kkk.convertlive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /**
     * 뷰 바인딩을 사용하여 UI 컴포넌트에 접근할 수 있도록 ActivityMainBinding 클래스의 인스턴스를 선언합니다.
     * lateinit 키워드를 사용하여 non-null 타입으로 선언하며, 초기화는 나중에 수행합니다.
     */
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * inflate 메서드를 사용하여 뷰 바인딩 클래스의 인스턴스를 초기화합니다.
         * 이를 통해 레이아웃에 정의된 뷰에 접근할 수 있게 됩니다.
         */
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 바인딩 클래스의 root 프로퍼티를 사용하여 레이아웃의 최상위 뷰를 얻고, 이를 setContentView 메서드에 전달하여 화면에 표시합니다.
        val view = binding.root
        setContentView(view)


        /**
         * 바인딩 객체를 사용하여 레이아웃에 정의된 뷰에 직접 접근합니다.
         * 이를 통해 findViewById를 사용하지 않고도 타입 안전하게 뷰에 접근할 수 있습니다.
         * 바인딩 객체를 사용하면 findViewById를 사용하지 않고도 뷰에 접근할 수 있습니다.
         * 바인딩 객체를 통해 뷰에 직접 접근하므로 NullPointerException의 위험이 없습니다.
         * 바인딩 객체를 사용하면 코드가 더 간결하고 안정적이며, 실행 시간의 성능도 향상됩니다.
         */
        val outputTextView = binding.outputTextView
        val outputUnitTextView = binding.outputUnitTextView
        val inputEditText = binding.inputEditText
        val inputUnitTextView = binding.inputUnitTextView

        /**
         * 아래의 findViewById를 사용하여 textView에 접근하는 시도는 NullPointerException을 발생시킵니다.
         * findViewById는 activity_main.xml 레이아웃에서 R.id.textView ID를 가진 뷰를 찾을 수 없으므로
         * 바인딩 객체를 무시하게 되어, NullPointerException이 발생합니다.
         *
         * val textView = findViewById<TextView>(R.id.textView) // 이 코드는 오류를 발생시킵니다.
         * textView.text = "안녕하세요"
         */
    }
}