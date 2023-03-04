package com.bill.springboot.movies.star.controller

import com.bill.springboot.movies.movie.service.MovieService
import com.bill.springboot.movies.star.domain.Star
import com.bill.springboot.movies.star.service.StarService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest
class StarApiControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var starService: StarService

    @MockkBean
    lateinit var movieService: MovieService

    private val starName = "Test Star"
    private val stars: List<Star> = listOf(Star("star 1"), Star("star 2"))
    val mapper = jacksonObjectMapper()

    @Test
    fun addStarWithStatus200() {
        // //init
        val starRequest = StarRequest(name = starName)
        val starResponse = StarResponse( name = starName)

        //given
        every { starService.create(starRequest) } returns starResponse

        //test
        mockMvc.perform(post("/v1/stars")
            .content(mapper.writeValueAsString(starRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun getStarsWithStatus200() {
        //given
        every { starService.findAll() } returns stars

        //test
       mockMvc.perform(get("/v1/stars"))
            .andExpect(status().isOk)
           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun findByNamedWithStatus200() {
       // init
        val star = Star(name= starName)
        //given
        every { starService.findByName(starName) } returns Optional.of(star)
        //test
        mockMvc.perform(get("/v1/stars/$starName"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("{\"name\":\"Test Star\"}"))
    }

    @Test
    fun deleteStarByNameWithStatus200() {
        // init
        val star = Star(name= starName)
        //given
        every { starService.findByName(starName) } returns Optional.of(star)
        every { starService.deleteByName(starName) } returns true
        //test
        mockMvc.perform(delete("/v1/stars/$starName"))
            .andExpect(status().isOk)
    }
}