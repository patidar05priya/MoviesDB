package com.bill.springboot.movies.star.integrationTests

import com.bill.springboot.movies.MoviesApplication
import com.bill.springboot.movies.star.controller.StarRequest
import com.bill.springboot.movies.star.controller.StarResponse
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
class StarApplicationIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val starName = "Test Star"

    @Test
    fun createMoviePostSuccess() {
        // //init
        val starRequest = StarRequest(name = starName)
        val result = restTemplate.postForEntity("/v1/stars", starRequest, StarResponse::class.java);
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(starName, result.getBody()?.name)
    }

    @Test
    fun getStarsSuccess() {
        val result = restTemplate.getForEntity("/v1/stars", List::class.java);
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
    }

    @Test
    fun findByNameSuccess() {
        val result = restTemplate.getForEntity("/v1/stars/$starName", Star::class.java);
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result?.statusCode)
    }

}