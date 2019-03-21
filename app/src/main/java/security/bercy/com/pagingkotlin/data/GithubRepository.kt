package security.bercy.com.pagingkotlin.data

import android.arch.lifecycle.MutableLiveData
import security.bercy.com.pagingkotlin.api.GithubService
import security.bercy.com.pagingkotlin.api.searchRepos
import security.bercy.com.pagingkotlin.db.GithubLocalCache
import security.bercy.com.pagingkotlin.model.RepoSearchResult

class GithubRepository(private val service : GithubService, private val cache: GithubLocalCache) {

    //keep the last request page, when the request is successful, increment the page number.
    private var lastRequestedPage = 1

    //LiveData of network errors
    private val networkErrors = MutableLiveData<String>()

    //avoid triggering multiple request in the same time
    private var isRequestInprogress = false



    fun search(query:String) : RepoSearchResult {
        lastRequestedPage = 1
        requestAndSaveData(query)

        //get data from local cache
        val data = cache.reposByName(query)

        return RepoSearchResult(data,networkErrors)
    }

    fun requestMore(query:String) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if(isRequestInprogress) return

        isRequestInprogress = true

        searchRepos(service,query,lastRequestedPage,NETWORK_PAGE_SIZE,{repos ->
            cache.insert(repos) {
                lastRequestedPage++
                isRequestInprogress = false
            }

        },{error ->
            networkErrors.postValue(error)
            isRequestInprogress = false
        })


    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

}
