package security.bercy.com.pagingkotlin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_repositories.*
import security.bercy.com.pagingkotlin.R

class SearchRepositoriesActivity : AppCompatActivity() {

    private val adapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repositories)
        initAdapter()
    }

    private fun initAdapter() {
        list.adapter = adapter


    }


}
