package kingmo.kkk.gitreposearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import kingmo.kkk.gitreposearch.adapter.UserAdapter
import kingmo.kkk.gitreposearch.databinding.ActivityMainBinding
import kingmo.kkk.gitreposearch.model.UserDto
import kingmo.kkk.gitreposearch.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    private val handler = Handler(Looper.getMainLooper())
    private var searchFor: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        userAdapter = UserAdapter {
            val intent = Intent(this@MainActivity, RepoActivity::class.java)
            intent.putExtra("username", it.username)
            startActivity(intent)
        }

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val runnable = Runnable {
            searchUser(searchFor)
        }

        binding.searchEditText.addTextChangedListener {
            searchFor = it.toString()
            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                300,
            )

            searchUser(it.toString())
        }
    }

    private fun searchUser(query: String) {
        val githubService = ApiClient.retrofit.create(GithubService::class.java)
        githubService.searchUsers(query).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("MainActivity", "Search User : ${response.body().toString()}")
                userAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}