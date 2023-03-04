package com.bill.springboot.movies.star.service

import com.bill.springboot.movies.star.controller.StarRequest
import com.bill.springboot.movies.star.controller.StarResponse
import com.bill.springboot.movies.star.domain.Star
import com.bill.springboot.movies.star.domain.StarRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class StarServiceTest {
    private final val starRepository: StarRepository = mockk()
    val starService = StarServiceImp(starRepository)

    private final val starName = "Test Star"
    val stars: Set<Star> = listOf(Star("star 1"), Star("star 2")).toSet()


    @Test
    fun createTest() {
        // mock
        val star = Star(name = starName)
        val starRequest = StarRequest(name = starName)
        val starResponse = StarResponse(name = starName)
        //given
        every { starRepository.save(star) } returns star;
        //when
        val result = starService.create(starRequest);
        //then
        assertEquals(starResponse, result)
    }

    @Test
    fun createAllTest() {
        // mock
        val star = Star(name = starName)
        //given
        every { starRepository.saveAll(listOf(star).toSet()) } returns listOf(star)
        //when
        val result = starService.createAll(listOf(star).toSet());
        //then
        assertEquals(true, result)
    }

    @Test
    fun findByNameTest() {
        // mock
        val star = Star(name = starName)
        //given
        every { starRepository.findByName(starName) } returns Optional.of(star)
        //when
        val result = starService.findByName(starName);
        //then
        assertEquals(star, result.get())
    }

    @Test
    fun findAllTest() {
        // mock
        val star = Star(name = starName)
        //given
        every { starRepository.findAll() } returns listOf(star)
        //when
        val result = starService.findAll();
        //then
        assertEquals(1, result.size)
    }

    @Test
    fun deleteByNameTest() {
        // mock
        val star = Star(name = starName)
        val starRequest = StarRequest(name = starName)
        val starResponse = StarResponse(name = starName)
        //given
        every { starRepository.deleteByName(starName) } returns true
        //when
        val result = starService.deleteByName(starName);
        //then
        assertEquals(true, result)
    }
}