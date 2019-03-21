package security.bercy.com.pagingkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search_repositories.*
import security.bercy.com.pagingkotlin.Injection
import security.bercy.com.pagingkotlin.R
import security.bercy.com.pagingkotlin.model.Repo

class SearchRepositoriesActivity : AppCompatActivity() {

    private val adapter = ReposAdapter()
    private lateinit var viewModel : SearchRepositoriesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repositories)

        viewModel = ViewModelProviders.of(this,Injection.provideViewModelFactory(this))
            .get(SearchRepositoriesViewModel::class.java)

        val decoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
        setupScrollListener()
        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY)?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY,viewModel.lastQueryValue())
    }

    private fun initAdapter() {
        list.adapter = adapter
        viewModel.repos.observe(this,Observer<List<Repo>> {
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(this,Observer<String> {
            Toast.makeText(this,"woops ${it}",Toast.LENGTH_LONG).show()
        })
    }

    private fun initSearch(query: String) {
        search_repo.setText(query)

        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                updateRepoListFromInput()
                true
            }else{
                false
            }
        }
        search_repo.setOnKeyListener { _, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            }else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        search_repo.text.trim().let {
            if(it.isNotEmpty()) {
                list.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
                adapter.submitList(null)//new list is avaliable
            }
        }

    }

    private fun showEmptyList(show: Boolean) {
        if(show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        }else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = list.layoutManager as LinearLayoutManager
        list.addOnScrollListener(object:RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount,lastVisibleItem,totalItemCount)
            }

        })
    }


    companion object {
        private const val LAST_SEARCH_QUERY : String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }

}
