package com.example.booksappcompose.data.remote.dto.search_dto

data class English(
    val decoding_demand_percentile: Int,
    val lexile: Int,
    val lexile_code: String,
    val measurable: Boolean,
    val mlf: Double,
    val msl: Double,
    val semantic_demand_percentile: Int,
    val structure_demand_percentile: Int,
    val syntactic_demand_percentile: Int
)