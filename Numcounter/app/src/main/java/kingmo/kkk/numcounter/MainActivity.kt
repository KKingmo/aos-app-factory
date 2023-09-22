package kingmo.kkk.numcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * `findViewById`를 사용하여 레이아웃에 정의된 뷰를 참조합니다.
         * 이 예제에서는 TextView와 두 개의 Button을 참조하고 있습니다.
         * 이를 통해 사용자 인터페이스의 상태를 관리하고 업데이트할 수 있습니다.
         */
        val numberTextView = findViewById<TextView>(R.id.numberTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val plusButton = findViewById<Button>(R.id.plusButton)

        var number = 0

        /**
         * `resetButton`에 `setOnClickListener`를 설정하여, 버튼이 클릭되면 `number`를 0으로 초기화하고
         * `numberTextView`의 텍스트를 업데이트합니다.
         * 이를 통해 사용자에게 현재 상태가 0임을 보여줍니다.
         */
        resetButton.setOnClickListener {
            number = 0
            numberTextView.text = number.toString()
            Log.d("onClick", "number $number")
        }

        /**
         * `plusButton`에도 `setOnClickListener`를 설정하여, 버튼이 클릭되면 `number`를 1 증가시키고
         * `numberTextView`의 텍스트를 업데이트합니다.
         * 이를 통해 사용자에게 현재 상태가 증가했음을 보여줍니다.
         */
        plusButton.setOnClickListener {
            number += 1
            numberTextView.text = number.toString()
            Log.i("onClick", "number $number")
        }
    }
}