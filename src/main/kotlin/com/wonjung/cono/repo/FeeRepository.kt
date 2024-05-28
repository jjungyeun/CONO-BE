package com.wonjung.cono.repo

import com.wonjung.cono.entity.Fee
import org.springframework.data.jpa.repository.JpaRepository

interface FeeRepository: JpaRepository<Fee, Long> {
}