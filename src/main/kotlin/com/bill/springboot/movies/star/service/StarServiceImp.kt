package com.bill.springboot.movies.star.service

import com.bill.springboot.movies.star.controller.StarRequest
import com.bill.springboot.movies.star.controller.StarResponse
import com.bill.springboot.movies.star.domain.Star
import com.bill.springboot.movies.star.domain.StarRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class StarServiceImp(private val starRepository: StarRepository) : StarService {
    override fun create(request: StarRequest): StarResponse {
        val star = starRepository.save(
            Star(name = request.name)
        )
        return StarResponse.from(star)
    }

    override fun createAll(request: Set<Star>): Boolean {
        val star = starRepository.saveAll(request)
        return true
    }

    override fun findByName(name: String): Optional<Star> {
        return starRepository.findByName(name)
    }

    override fun deleteByName(name: String) : Boolean{
        return starRepository.deleteByName(name)
    }

    override fun findAll(): List<Star> {
      return starRepository.findAll();
    }
}