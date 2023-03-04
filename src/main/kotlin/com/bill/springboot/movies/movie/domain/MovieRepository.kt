package com.bill.springboot.movies.movie.domain

import com.bill.springboot.movies.movie.domain.data.CompositeMovieId
import com.bill.springboot.movies.movie.domain.data.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, CompositeMovieId>  {
    fun findByCompositeMovieIdReleaseDate(releaseDate: String): Set<Movie>
    fun findByCompositeMovieIdTitle(title: String): Set<Movie>
}