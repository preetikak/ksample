package com.preetika.katerassignment.model

import com.google.gson.annotations.SerializedName

data class Response(
        @SerializedName("data") val data: List<Data>,
        @SerializedName("pagination") val pagination: Pagination,
        @SerializedName("meta") val meta:  Meta
)

data class Pagination(
        val total_count: Int,
        val count: Int
)
data class Data(
        val images: Images

)

data class Meta(
        val status: Int,
        val msg: String
)
data class Images(
        val preview_gif: Preview
)
data class Preview(
        @SerializedName("url") val gifUrl: String,
        var isFavorite : Boolean = false
)
