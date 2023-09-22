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
         * 여기서 `findViewById` 메서드를 사용하여 XML 레이아웃 파일에서 정의된 뷰들을 참조합니다.
         * 이 메서드를 통해 뷰의 인스턴스에 접근할 수 있게 되어, 이후에 이 뷰들을 조작할 수 있게 됩니다.
         */
        val numberTextView = findViewById<TextView>(R.id.numberTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val plusButton = findViewById<Button>(R.id.plusButton)

        /**
         * `setOnClickListener` 메서드를 사용하여 버튼에 클릭 리스너를 설정합니다.
         * 이 리스너는 버튼이 클릭될 때 실행될 코드 블록을 정의합니다.
         * 여기서는 버튼들이 클릭되면 로그 메시지를 출력합니다.
         */
        resetButton.setOnClickListener {
            Log.d("onClick", "리셋 버튼이 클릭 됐습니다.")
        }

        plusButton.setOnClickListener {
            Log.i("onClick", "플러스 버튼이 클릭 됐습니다")
        }
    }
}