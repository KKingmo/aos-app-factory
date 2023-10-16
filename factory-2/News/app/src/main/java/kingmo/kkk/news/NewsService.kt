package kingmo.kkk.news

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("rss/30000001/")
    fun mainFeed(): Call<NewsRss>
}