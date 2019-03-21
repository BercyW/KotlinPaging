package security.bercy.com.pagingkotlin.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import security.bercy.com.pagingkotlin.data.GithubRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchRepositoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchRepositoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}