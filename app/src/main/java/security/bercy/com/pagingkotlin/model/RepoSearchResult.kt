package security.bercy.com.pagingkotlin.model

import android.arch.lifecycle.LiveData

data class RepoSearchResult(
    val data:LiveData<List<Repo>>,
    val networkError: LiveData<String>
)