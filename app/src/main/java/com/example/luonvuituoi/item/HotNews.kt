package com.example.luonvuituoi.item

data class HotNews (
    val status: String,
    val totalResults: Long,
    val articles: List<HotNewItem>
)