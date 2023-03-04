package com.bill.springboot.movies.movie.service

import com.bill.springboot.movies.movie.controller.MovieRequest
import com.bill.springboot.movies.movie.controller.MovieResponse
import com.bill.springboot.movies.movie.domain.MovieRepository
import com.bill.springboot.movies.movie.domain.data.CompositeMovieId
import com.bill.springboot.movies.movie.domain.data.Movie
import com.bill.springboot.movies.star.domain.Star
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MovieServiceTest {
    private final val movieRepository: MovieRepository = mockk()
    val movieService = MovieServiceImp(movieRepository)
    private final val testReleaseDate = "2022-01-01"
    private final val testTitle = "testTitle"
    private final val description = "this is a test"
    private final val id : Long = 0
    val stars: Set<Star> = listOf(Star("star 1"), Star("star 2")).toSet()


    @Test
    fun createTest() {
        // mock
        val compositeMovieId = CompositeMovieId(testReleaseDate, testTitle)
        val movie = Movie(
            compositeMovieId = compositeMovieId,
            description = description,
            stars = stars,
        )
        val movieRequest = MovieRequest(
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = listOf("star 1", "star 2").toSet()
        )

        val movieResponse = MovieResponse(
            id = id,
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = stars
        )

        //given
        every { movieRepository.save(movie) } returns movie;

        //when
        val result = movieService.create(movieRequest, stars);

        //then
        assertEquals(movieResponse, result)
    }

    @Test
    fun findByReleaseDateTest() {
        // mock
        val compositeMovieId = CompositeMovieId(testReleaseDate, testTitle)
        val movie = Movie(
            compositeMovieId = compositeMovieId,
            description = description,
            stars = stars,
        )
        val movies: Set<Movie> = listOf(movie).toSet()
        val movieResponse = MovieResponse(
            id = id,
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = stars
        )

        //given
        every { movieRepository.findByCompositeMovieIdReleaseDate(testReleaseDate) } returns movies;

        //when
        val result = movieService.findByReleaseDate(testReleaseDate);

        //then
        assertEquals(listOf(movieResponse).toSet(), result)
    }

    @Test
    fun findByTitleTest() {
        // mock
        val compositeMovieId = CompositeMovieId(testReleaseDate, testTitle)
        val movie = Movie(
            compositeMovieId = compositeMovieId,
            description = description,
            stars = stars,
        )
        val movies: Set<Movie> = listOf(movie).toSet()
        val movieResponse = MovieResponse(
            id = id,
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = stars
        )

        //given
        every { movieRepository.findByCompositeMovieIdTitle(testTitle) } returns movies;

        //when
        val result = movieService.findByTitle(testTitle);

        //then
        assertEquals(listOf(movieResponse).toSet(), result)
    }


}