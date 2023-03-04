package com.bill.springboot.movies.movie.controller

import com.bill.springboot.movies.movie.domain.data.CompositeMovieId
import com.bill.springboot.movies.movie.domain.data.Movie
import com.bill.springboot.movies.movie.service.MovieService
import com.bill.springboot.movies.movie.validation.MoiveRequestValidator
import com.bill.springboot.movies.star.domain.Star
import com.bill.springboot.movies.star.service.StarService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/movie")
class MovieApiController(private val movieService: MovieService, private val starService: StarService) {

    @PostMapping
    fun addMovie(@RequestBody request: MovieRequest): ResponseEntity<MovieRequest> {
        if (MoiveRequestValidator.checkDate(request.releaseDate) &&
            MoiveRequestValidator.hasStars(request.stars)
        ) {
            val stars = getListStar(request.stars)
            starService.createAll(stars)
            movieService.create(request, stars)
            return ResponseEntity.ok(request)
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/title/{title}")
    fun findByTitle(@PathVariable("title") title: String): Set<MovieResponse> {
        return movieService.findByTitle(title);
    }

    @DeleteMapping("/{releaseDate}/{title}")
    fun deleteMovie(@PathVariable("releaseDate") releaseDate: String, @PathVariable("title") title: String): ResponseEntity<Movie> {
        if (MoiveRequestValidator.checkDate(releaseDate) && MoiveRequestValidator.titleISValid(title)) {
            val compositeMovieId = CompositeMovieId(releaseDate, title)
            val movie = Movie(compositeMovieId = compositeMovieId, description = "", stars = HashSet())
            if(movieService.deleteById(movie)){
                println(releaseDate)
                return ResponseEntity.ok(movie)
            }
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/releaseDate/{releaseDate}")
    fun findByReleaseDate(@PathVariable("releaseDate") releaseDate: String): ResponseEntity<Set<MovieResponse>> {
        if (MoiveRequestValidator.checkDate(releaseDate)) {
            return ResponseEntity.ok(movieService.findByReleaseDate(releaseDate))
        }
        return ResponseEntity.badRequest().build();
    }

    fun getListStar(stars: Set<String>): HashSet<Star> {
        var res = HashSet<Star>()
        for (item in stars) {
            res.add(Star(name = item))
        }
        return res
    }
}