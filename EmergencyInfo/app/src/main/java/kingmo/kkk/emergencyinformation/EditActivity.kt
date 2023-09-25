package kingmo.kkk.emergencyinformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        /**
         * MainActivity에서 전달된 메시지를 "intentMessage" 키를 사용하여 가져옵니다.
         */
        val message = intent.getStringExtra("intentMessage") ?: "없음"
        Log.d("intentMessage", message)
    }
}