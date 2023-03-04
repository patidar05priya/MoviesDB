package com.bill.springboot.movies.star.service

import com.bill.springboot.movies.star.controller.StarRequest
import com.bill.springboot.movies.star.controller.StarResponse
import com.bill.springboot.movies.star.domain.Star
import java.util.*

interface StarService {
    fun create(request: StarRequest): StarResponse
    fun createAll(request: Set<Star>): Boolean
    fun findByName(name: String): Optional<Star>
    fun deleteByName(name: String): Boolean
    fun findAll(): List<Star>

}