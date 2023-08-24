package com.glantrox.dicoding_android_fundamental.core.models


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: UsersResponse,
    @SerializedName("total_count")
    val totalCount: Int
)