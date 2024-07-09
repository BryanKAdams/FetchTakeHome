package com.bryankeltonadams.fetchtakehometest.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int? = null,
    val name: String? = null,
    val listId: Int,
)