package com.bill.springboot.movies.movie.service

import com.bill.springboot.movies.movie.controller.MovieRequest
import com.bill.springboot.movies.movie.controller.MovieResponse
import com.bill.springboot.movies.movie.domain.MovieRepository
import com.bill.springboot.movies.movie.domain.data.CompositeMovieId
import com.bill.springboot.movies.movie.domain.data.Movie
import com.bill.springboot.movies.star.domain.Star
import org.springframework.stereotype.Service

@Service
class MovieServiceImp (
    private val movieRepository: MovieRepository) : MovieService {

    override fun create(request: MovieRequest, stars: Set<Star>): MovieResponse {
        val compositeMovieId = CompositeMovieId(request.releaseDate, request.title)
        val movie = movieRepository.save(
            Movie(
                compositeMovieId = compositeMovieId,
                description = request.description,
                stars = stars,
            )
        )
        return MovieResponse.from(movie)

    }

    override fun findByReleaseDate(releaseDate: String): Set<MovieResponse> {
        return getListOfMovieRes(movieRepository.findByCompositeMovieIdReleaseDate(releaseDate));
    }

    override fun findByTitle(title: String): Set<MovieResponse> {
        return getListOfMovieRes(movieRepository.findByCompositeMovieIdTitle(title));
    }

    override fun deleteById(movie: Movie): Boolean {
        if(movieRepository.existsById(movie.compositeMovieId)){
            movieRepository.delete(movie)
            return true
        }
        return false;
    }

    fun getListOfMovieRes(list: Set<Movie>) : Set<MovieResponse>{
        var result = HashSet<MovieResponse>()
        for(item in list){
            result.add(MovieResponse(
                id = item.id,
                title = item.compositeMovieId.title,
                releaseDate = item.compositeMovieId.releaseDate,
                description = item.description,
                stars = item.stars
            ))
        }
        return result;
    }
}