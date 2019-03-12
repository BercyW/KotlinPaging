package security.bercy.com.pagingkotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import security.bercy.com.pagingkotlin.R
import security.bercy.com.pagingkotlin.model.Repo


class RepoViewHolder( view: View) : RecyclerView.ViewHolder(view) {
    private val name : TextView = view.findViewById(R.id.repo_name)
    private val description : TextView = view.findViewById(R.id.repo_description)
    private val starts : TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)
    private var repo:Repo? = null

    fun build(repo : Repo?) {
        if(repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility=View.GONE
            language.visibility=View.GONE
            starts.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        }else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo:Repo) {
        this.repo = repo
        name.text = repo.fullName

        var descriptionVisibility = View.GONE
        if(repo.description!=null) {
            description.text=repo.description
            descriptionVisibility=View.VISIBLE
        }
        description.visibility = descriptionVisibility

        starts.text = repo.stars.toString()
        forks.text =repo.forks.toString()

        var languageVisibility = View.GONE
        if(!repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language,repo.language)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility
    }


    companion object {
        fun create(parent: ViewGroup) : RepoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_view_item,parent,false)
            return RepoViewHolder(view)
        }
    }
}