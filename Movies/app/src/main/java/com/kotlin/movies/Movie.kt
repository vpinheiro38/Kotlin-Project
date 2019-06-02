package com.kotlin.movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("overview")
    @Expose
    val overview: String,

    @SerializedName("poster_path")
    @Expose
    val posterpath: String
)