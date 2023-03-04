package com.bill.springboot.movies.star.domain

import com.bill.springboot.movies.movie.domain.data.Movie
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.Hibernate
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "startable")
data class Star(


    @Column(name="name")
    @Id @get: NotBlank
    val name: String = "",

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Star

        return name != null && name == other.name
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name )"
    }
}