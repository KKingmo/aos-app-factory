package kingmo.kkk.dailynotices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient()

        val request: Request = Request.Builder().url("http://10.0.2.2:8080").build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Client", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.e("Client", "${response.body?.string()}")
                }
            }
        }

        client.newCall(request).enqueue(callback)

    }
}