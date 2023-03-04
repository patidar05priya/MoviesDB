package com.bill.springboot.movies.movie.controller

import com.bill.springboot.movies.star.domain.Star

data class MovieRequest(
    val title: String,
    val releaseDate: String,
    val description: String,
    val stars : Set<String>
)