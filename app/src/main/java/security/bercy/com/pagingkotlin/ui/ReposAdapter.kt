package security.bercy.com.pagingkotlin.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import security.bercy.com.pagingkotlin.model.Repo

/**
 * Using ListAdapter in recylerview to compute diffs between lists on a background thread.
 */
class ReposAdapter : ListAdapter<Repo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return RepoViewHolder.create(p0)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val repoItem = getItem(p1)
        if(repoItem!=null) {
            (p0 as RepoViewHolder).build(repoItem)
        }
    }


    companion object {
        private val REPO_COMPARATOR = object:DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(p0: Repo, p1: Repo): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun areContentsTheSame(p0: Repo, p1: Repo): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }
}