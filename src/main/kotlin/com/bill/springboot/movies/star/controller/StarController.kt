package com.bill.springboot.movies.star.controller

import com.bill.springboot.movies.movie.controller.MovieRequest
import com.bill.springboot.movies.star.domain.Star
import com.bill.springboot.movies.star.service.StarService

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/stars")
class StarController(private val starService : StarService) {

    @GetMapping
    fun getAllStars(): List<Star> {
        return  starService.findAll()
    }


    @PostMapping
    fun addNewStar(@Valid @RequestBody star: StarRequest): ResponseEntity<StarResponse>  {
        return ResponseEntity.ok(starService.create(star))
    }


    @GetMapping("{name}")
    fun findByName(@PathVariable(value = "name") id: String): ResponseEntity<Star> {
        val starById = starService.findByName(id)
        if(starById.isPresent){
            return ResponseEntity.ok(starById.get())
        }
        return ResponseEntity.notFound().build();
    }
}