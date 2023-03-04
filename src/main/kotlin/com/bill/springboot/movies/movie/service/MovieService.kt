package com.bill.springboot.movies.movie.service

import com.bill.springboot.movies.movie.controller.MovieRequest
import com.bill.springboot.movies.movie.controller.MovieResponse
import com.bill.springboot.movies.movie.domain.data.Movie
import com.bill.springboot.movies.star.domain.Star

interface MovieService {

    fun create(request: MovieRequest,  stars: Set<Star>): MovieResponse
    fun findByReleaseDate(releaseDate: String): Set<MovieResponse>
    fun findByTitle(title: String): Set<MovieResponse>
    fun deleteById(movie: Movie): Boolean
}