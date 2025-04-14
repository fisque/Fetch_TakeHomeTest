package com.jennywu.fetchtakehome.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListItem(
    @Json(name = "id") val id: Int,
    @Json(name = "listId") val listId: Int,
    @Json(name = "name") val name: String?
)
