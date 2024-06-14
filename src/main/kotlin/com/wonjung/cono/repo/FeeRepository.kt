package com.wonjung.cono.repo

import com.wonjung.cono.entity.Cono
import com.wonjung.cono.entity.Fee
import org.springframework.data.jpa.repository.JpaRepository

interface FeeRepository: JpaRepository<Fee, Long> {
    fun findAllByCono(cono: Cono): List<Fee>
}