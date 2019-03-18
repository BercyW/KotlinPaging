package security.bercy.com.pagingkotlin.api

import com.google.gson.annotations.SerializedName
import security.bercy.com.pagingkotlin.model.Repo

data class RepoSearchResponse(
    @SerializedName("total_count") val total:Int = 0,
    @SerializedName("items") val items:List<Repo> = emptyList(),
    val nextPage:Int? = null

)