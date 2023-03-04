package com.bill.springboot.movies.star.controller
import com.bill.springboot.movies.star.domain.Star

data class StarResponse(
    val name: String
) {
    companion object{
        fun from(star: Star) = StarResponse(
            name = star.name
        )
    }
}