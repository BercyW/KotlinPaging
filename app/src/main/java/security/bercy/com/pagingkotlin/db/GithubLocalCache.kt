package security.bercy.com.pagingkotlin.db

import android.arch.lifecycle.LiveData
import android.content.IntentSender
import security.bercy.com.pagingkotlin.model.Repo
import java.util.concurrent.Executor

class GithubLocalCache(private val repoDao:RepoDao,private val ioExecutor: Executor) {

    fun insert(repos:List<Repo>,insertFinished: ()->Unit) {
        ioExecutor.execute {
            repoDao.insert(repos)
            insertFinished()
        }

    }

    fun reposByName(name:String) : LiveData<List<Repo>> {
        //%模糊查找之前或者之后
        val query = "%${name.replace(' ','%')}%"
        return repoDao.reposByName(query)
    }

}