package com.bill.springboot.movies.movie.domain.data

import com.bill.springboot.movies.star.domain.Star
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import kotlin.collections.HashSet

@Entity
@Table(name = "movietable",
    indexes = [Index(name = "IDX_MYIDX1", columnList = "id") ])
data class Movie(
    @EmbeddedId
    val compositeMovieId: CompositeMovieId = CompositeMovieId("",""),

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    val id: Long = 0,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(name = "movie_actor",
        joinColumns = [JoinColumn(name = "releaseDate", referencedColumnName = "releaseDate"),
            JoinColumn(name = "title",referencedColumnName = "title")],
        inverseJoinColumns = [JoinColumn(name = "name")])
    @JsonIgnore
    var stars: Set<Star> = HashSet<Star>(),


    @get: NotBlank
    @Column(name="description")
    val description: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Movie

        return id == other.id
                && compositeMovieId == other.compositeMovieId
    }

    override fun hashCode(): Int = Objects.hash(compositeMovieId);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(EmbeddedId = $compositeMovieId , id = $id )"
    }
}