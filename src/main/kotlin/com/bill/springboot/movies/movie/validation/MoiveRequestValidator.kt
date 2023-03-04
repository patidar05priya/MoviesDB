package com.bill.springboot.movies.movie.validation

import com.bill.springboot.movies.movie.controller.MovieResponse
import com.bill.springboot.movies.movie.domain.data.Movie
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MoiveRequestValidator {
    companion object{
        fun checkDate(dateStr: String): Boolean {
            return try {
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                formatter.parse(dateStr)
                true;
            }catch (exception:  Exception){
                false;
            }
        }

        fun hasStars(stars: Set<String>): Boolean {
            return stars.isNotEmpty()
        }

        fun titleISValid(title: String): Boolean {
            return title.isNotEmpty()
        }
    }
}