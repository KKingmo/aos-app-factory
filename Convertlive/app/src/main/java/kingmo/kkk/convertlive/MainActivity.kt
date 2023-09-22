package kingmo.kkk.convertlive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import kingmo.kkk.convertlive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /**
     * 뷰 바인딩을 사용하여 UI 컴포넌트에 접근할 수 있도록 ActivityMainBinding 클래스의 인스턴스를 선언합니다.
     * lateinit 키워드를 사용하여 non-null 타입으로 선언하며, 초기화는 나중에 수행합니다.
     */
    private lateinit var binding: ActivityMainBinding
    var cmToM = true
    var inputNumber: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * inflate 메서드를 사용하여 뷰 바인딩 클래스의 인스턴스를 초기화합니다.
         * 이를 통해 레이아웃에 정의된 뷰에 접근할 수 있게 됩니다.
         */
        binding = ActivityMainBinding.inflate(layoutInflater)

        /**
         * setContentView에 binding.root를 전달하여, 화면에 레이아웃의 최상위 뷰를 표시합니다.
         */
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
        val swapImageButton = binding.swapImageButton

        /**
         * addTextChangedListener를 사용하여 inputEditText의 텍스트가 변경될 때마다 이벤트를 받아옵니다.
         * 텍스트가 변경될 때마다, 입력된 값을 사용하여 변환된 값을 outputTextView에 표시합니다.
         */
        inputEditText.addTextChangedListener { text ->
            inputNumber = if (text.isNullOrEmpty()) {
                0
            } else {
                text.toString().toInt()
            }

            if (cmToM) {
                outputTextView.text = inputNumber.times(0.01).toString()
            } else {
                outputTextView.text = inputNumber.times(100).toString()
            }
        }

        /**
         * swapImageButton에 setOnClickListener를 설정하여, 버튼이 클릭될 때마다 이벤트를 받아옵니다.
         * 버튼이 클릭되면, 단위 변환 방식을 변경하고, 관련 텍스트 뷰의 텍스트를 업데이트합니다.
         */
        swapImageButton.setOnClickListener {
            cmToM = cmToM.not()
            if (cmToM) {
                inputUnitTextView.text = "cm"
                outputUnitTextView.text = "m"
                outputTextView.text = inputNumber.times(0.01).toString()
            } else {
                inputUnitTextView.text = "m"
                outputUnitTextView.text = "cm"
                outputTextView.text = inputNumber.times(100).toString()
            }
        }
    }

    /**
     * onSaveInstanceState 메서드를 오버라이드하여 액티비티의 상태를 저장합니다.
     * 이 메서드는 시스템이 액티비티를 종료하기 전에 호출되어, 액티비티의 상태를 저장할 수 있게 합니다.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("cmToM", cmToM)
        super.onSaveInstanceState(outState)
    }

    /**
     * onRestoreInstanceState 메서드를 오버라이드하여 액티비티의 상태를 복원합니다.
     * 이 메서드는 액티비티가 재생성될 때 호출되어, 이전에 저장된 상태를 복원할 수 있게 합니다.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmToM = savedInstanceState.getBoolean("cmToM")
        Log.d("cmToM", cmToM.toString())
        binding.inputUnitTextView.text = if (cmToM) "cm" else "m"
        binding.outputUnitTextView.text = if (cmToM) "m" else "cm"
        super.onRestoreInstanceState(savedInstanceState)
    }
}