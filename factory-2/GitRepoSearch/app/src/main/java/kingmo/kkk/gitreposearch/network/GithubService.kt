package kingmo.kkk.gitreposearch.network

import kingmo.kkk.gitreposearch.model.Repo
import kingmo.kkk.gitreposearch.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
//    @Headers("Authorization: Bearer 비밀~")
    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>

//    @Headers("Authorization: Bearer 비밀~")
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UserDto>
}