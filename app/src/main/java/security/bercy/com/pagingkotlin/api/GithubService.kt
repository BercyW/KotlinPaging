package security.bercy.com.pagingkotlin.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import security.bercy.com.pagingkotlin.model.Repo


private const val IN_QUALIFIER = "in:name,description"

fun searchRepos(
               service:GithubService,
               query:String,
               page:Int,
               itemsPerPage:Int,
               onSuccess:(repos:List<Repo>)->Unit,
               onError:(error:String)->Unit) {

    val apiQuery = query + IN_QUALIFIER

    service.searchRepos(apiQuery,page,itemsPerPage).enqueue(
        object : Callback<RepoSearchResponse> {
            override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                onError(t.message?:"unknown error")
            }

            override fun onResponse(call: Call<RepoSearchResponse>?, response: Response<RepoSearchResponse>) {
                if(response.isSuccessful) {
                    val repos = response.body()?.items ?: emptyList()
                    onSuccess(repos)
                }else {
                    onError(response.errorBody()?.string()?:"unknown error")
                }
            }


        }
    )
}

interface GithubService {
    @GET("search/repositories?sort=stars")
    fun searchRepos(@Query("q") query:String,
                    @Query("page")page:Int,
                    @Query("per_page") itemsPerPage: Int) : Call<RepoSearchResponse>
    companion object {
        private const  val BASE_URL="https://api.github.com/"

        fun create() : GithubService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }

}