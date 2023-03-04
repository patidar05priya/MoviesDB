package com.bill.springboot.movies.movie.domain.data
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class CompositeMovieId(
    @get: NotBlank
    @Column(name="releaseDate")
    val releaseDate: String = "",

    @get: NotBlank
    @Column(name="title")
    val title: String = "",
) : Serializable