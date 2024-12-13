package com.satria.gymer.ui.model

data class Item(
    val icon: String,
    val title: String,
    val subtitle: String,
    var isFavorite: Boolean = false
)
