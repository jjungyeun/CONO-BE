package com.wonjung.cono.repo

import com.wonjung.cono.entity.Cono
import org.springframework.data.jpa.repository.JpaRepository

interface ConoRepository: JpaRepository<Cono, Long> {
}