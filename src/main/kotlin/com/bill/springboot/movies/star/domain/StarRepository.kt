package com.bill.springboot.movies.star.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StarRepository : JpaRepository<Star, Long>  {
    fun findByName(name: String): Optional<Star>
    fun deleteByName(name: String) : Boolean
}