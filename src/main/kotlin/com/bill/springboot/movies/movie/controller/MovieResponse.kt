package com.bill.springboot.movies.movie.controller

import com.bill.springboot.movies.movie.domain.data.Movie
import com.bill.springboot.movies.star.domain.Star

data class MovieResponse(
    val id: Long,
    val title: String,
    val releaseDate: String,
    val description: String,
    val stars : Set<Star>
){
    companion object{
        fun from(movie: Movie) = MovieResponse(
            id = movie.id,
            title = movie.compositeMovieId.title,
            releaseDate = movie.compositeMovieId.releaseDate,
            description = movie.description,
            stars = movie.stars
        )
    }
}