package com.bill.springboot.movies.movie.integrationTest

import com.bill.springboot.movies.MoviesApplication
import com.bill.springboot.movies.movie.controller.MovieRequest
import com.bill.springboot.movies.movie.controller.MovieResponse
import com.bill.springboot.movies.star.domain.Star
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.http.HttpStatus


@SpringBootTest(
    classes = [MoviesApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoviesApplicationIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private final val testReleaseDate = "2022-01-01"
    private final val testTitle = "testTitle"
    private final val description = "this is a test"
    private final val id : Long = 0
    private val stars: Set<Star> = listOf(Star("star 1"), Star("star 2")).toSet()

    @Test
    fun createMoviePostSuccess() {
        // init
        val movieRequest = MovieRequest(
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = listOf("star 1", "star 2").toSet()
        )

        val result = restTemplate.postForEntity("/v1/movie", movieRequest, MovieRequest::class.java);

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(testTitle, result.getBody()?.title)
        assertEquals(testReleaseDate, result.getBody()?.releaseDate)
    }
    @Test
    fun createMoviePostBadRequest() {
        // init
        val movieRequest = MovieRequest(
            title = testTitle,
            releaseDate = "2020202020",
            description = description,
            stars = listOf("star 1", "star 2").toSet()
        )
        val result = restTemplate.postForEntity("/v1/movie", movieRequest, MovieRequest::class.java);
        assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode)

    }

    @Test
    fun getMovieByTitleSuccess() {
        val result = restTemplate.getForEntity("/v1/movie/title/$testTitle", Set::class.java);
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(1, result.getBody()?.size)
    }

    @Test
    fun getMovieByReleaseDateSuccess() {
        val result = restTemplate.getForEntity("/v1/movie/releaseDate/$testReleaseDate", Set::class.java);
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(1, result.getBody()?.size)
    }

    @Test
    fun getMovieByReleaseDateBadRequest() {
        val result = restTemplate.getForEntity("/v1/movie/releaseDate/2020202020", Set::class.java);
        assertNotNull(result)
        assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode)
    }
}