package kingmo.kkk.emergencyinformation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kingmo.kkk.emergencyinformation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 'goInputActivityButton'이 클릭되면, EditActivity로 이동하기 위한 Intent가 생성됩니다.
         * 또한, "응급의료정보"라는 메시지를 putExtra 메서드를 사용하여 EditActivity로 전달합니다.
         * 여기서 'binding.goInputActivityButton.setOnClickListener'는 'goInputActivityButton' 버튼이 클릭되었을 때 수행될 동작을 정의합니다.
         * 'Intent(this, EditActivity::class.java)'는 명시적 Intent를 생성합니다. 이 Intent는 현재 액티비티에서 EditActivity로의 전환을 나타냅니다.
         * 'intent.putExtra("intentMessage","응급의료정보")'는 Intent에 추가 데이터를 삽입합니다. 이 데이터는 "intentMessage"라는 키로 EditActivity에서 접근 가능합니다.
         * 'startActivity(intent)'는 생성된 Intent를 사용하여 EditActivity를 시작합니다.
         */
        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java) // 명시적 Intent 생성
            intent.putExtra("intentMessage","응급의료정보") // "응급의료정보" 메시지를 Intent에 추가
            startActivity(intent) // EditActivity 시작
        }
    }
}