package security.bercy.com.pagingkotlin.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import security.bercy.com.pagingkotlin.data.GithubRepository
import security.bercy.com.pagingkotlin.model.Repo
import security.bercy.com.pagingkotlin.model.RepoSearchResult

class SearchRepositoriesViewModel(private val repository : GithubRepository) : ViewModel(){
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
    private val queryLiveData= MutableLiveData<String>()
    private val repoResult:LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val repos:LiveData<List<Repo>> = Transformations.switchMap(repoResult) {it->it.data}
    val networkErrors:LiveData<String> = Transformations.switchMap(repoResult) {it->it.networkError}


    fun searchRepo(queryString:String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount : Int, lastVisibleItemPosition : Int,totalItemCount : Int) {
        if(visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if(immutableQuery !=null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

     fun lastQueryValue(): String?  = queryLiveData.value

}