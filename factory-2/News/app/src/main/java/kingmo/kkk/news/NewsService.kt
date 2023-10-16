package kingmo.kkk.news

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("browse/feed/")
    fun mainFeed(): Call<NewsRss>

    @GET("category/news/politics/feed/")
    fun politicsNews(): Call<NewsRss>

    @GET("category/news/economy/feed/")
    fun economyNews(): Call<NewsRss>

    @GET("category/news/society/feed/")
    fun societyNews(): Call<NewsRss>

    @GET("category/news/sports/feed/")
    fun sportsNews(): Call<NewsRss>

    @GET("category/news/culture/feed/")
    fun cultureNews(): Call<NewsRss>

}