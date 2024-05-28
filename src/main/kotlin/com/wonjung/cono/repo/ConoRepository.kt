package com.wonjung.cono.repo

import com.wonjung.cono.entity.Cono
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ConoRepository: JpaRepository<Cono, Long> {
    fun findByNameContaining(name: String, pageable: Pageable): Page<Cono>
}