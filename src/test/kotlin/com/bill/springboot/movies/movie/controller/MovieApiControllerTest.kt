package com.bill.springboot.movies.movie.controller

import org.springframework.http.MediaType
import com.bill.springboot.movies.movie.service.MovieService
import com.bill.springboot.movies.star.domain.Star
import com.bill.springboot.movies.star.service.StarService
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every


@WebMvcTest
class MovieApiControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var movieService: MovieService

    @MockkBean
    lateinit var starService: StarService

    private val testReleaseDate = "2022-01-01"
    private val testTitle = "testTitle"
    private val description = "this is a test"
    private val id : Long = 0
    private val stars: Set<Star> = listOf(Star("star 1"), Star("star 2")).toSet()
    val mapper = jacksonObjectMapper()


    @Test
    fun addMovieWithStatus200() {
        // //init
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
        every { starService.createAll(stars) } returns true
        every { movieService.create(movieRequest, stars) } returns movieResponse;
        //test
        mockMvc.perform(post("/v1/movie")
            .content(mapper.writeValueAsString(movieRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun addMovieWithStatusBadReleaseDateWithStatus400() {
        // init
        val movieRequest = MovieRequest(
            title = testTitle,
            releaseDate = "2020-2020-20",
            description = description,
            stars = listOf("star 1", "star 2").toSet()
        )
        //test
        mockMvc.perform(post("/v1/movie")
            .content(mapper.writeValueAsString(movieRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun addMovieWithEmptyStarWithStatus400() {
        // init
        val movieRequest = MovieRequest(
            title = testTitle,
            releaseDate = "2020-2020-20",
            description = description,
            stars = HashSet()
        )
        //test
        mockMvc.perform(post("/v1/movie")
            .content(mapper.writeValueAsString(movieRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun findByReleaseDateWithStatus400() {
        // init
        val movieResponse = MovieResponse(
            id = id,
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = stars
        )
        //given
        every { movieService.findByReleaseDate(testReleaseDate)} returns listOf(movieResponse).toSet()
        //test
        mockMvc.perform(get("/v1/movie/releaseDate/2020-2020-20")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
    }
    @Test
    fun findByTitleWithStatus200() {
        // init
        val movieResponse = MovieResponse(
            id = id,
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = stars
        )
        //given
        every { movieService.findByTitle(testTitle)} returns listOf(movieResponse).toSet()
        //test
        mockMvc.perform(get("/v1/movie/title/$testTitle"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun findByReleaseDateWithStatus200() {
        // init
        val movieResponse = MovieResponse(
            id = id,
            title = testTitle,
            releaseDate = testReleaseDate,
            description = description,
            stars = stars
        )
        //given
        every { movieService.findByReleaseDate(testReleaseDate)} returns listOf(movieResponse).toSet()
        //test
        mockMvc.perform(get("/v1/movie/releaseDate/$testReleaseDate"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}