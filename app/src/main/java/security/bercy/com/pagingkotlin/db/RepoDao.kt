package security.bercy.com.pagingkotlin.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import security.bercy.com.pagingkotlin.model.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts:List<Repo>)

    @Query("SELECT * FROM repos WHERE (name LIKE :queryString) OR (description LIKE " + ":queryString) ORDER BY stars DESC, name ASC")
    fun reposByName(queryString: String) : LiveData<List<Repo>>
}
//@Query("SELECT * FROM repos WHERE (name LIKE :queryString) OR (description LIKE " + ":queryString) ORDER BY stars DESC, name ASC")


//not working
//@Query("SELECT * FROM repos WHERE (name LIKE : queryString) OR (description LIKE " + ":queryString) ORDER BY stars DESC, name ASC")