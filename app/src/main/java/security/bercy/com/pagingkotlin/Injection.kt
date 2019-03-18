package security.bercy.com.pagingkotlin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import security.bercy.com.pagingkotlin.api.GithubService
import security.bercy.com.pagingkotlin.data.GithubRepository
import security.bercy.com.pagingkotlin.db.GithubLocalCache
import security.bercy.com.pagingkotlin.db.RepoDatabase
import security.bercy.com.pagingkotlin.ui.ViewModelFactory
import java.util.concurrent.Executors


/**
 * class that handles object creation
 * Like this, objects can be passed as parameters in the constructors and then replaced for testing, where needed.
 */
object Injection {

    /**
     * provides the [ViewModelProvider.Factor] that is then used to get a reference to [ViewModel] objects.
     *
     */
    fun provideViewModelFactory(context:Context) : ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }

    /**
     * create an instance of githubrepository based on githubService and a githublocalcache
     */
    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(),provideCache(context))
    }

    /**
     * creates an instance of githublocalcache based on the database dao
     */
    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepoDatabase.getInstance(context)
        return GithubLocalCache(database.reposDao(),Executors.newSingleThreadExecutor())
    }

}