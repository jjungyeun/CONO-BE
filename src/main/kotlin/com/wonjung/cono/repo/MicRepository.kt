package com.wonjung.cono.repo

import com.wonjung.cono.entity.Mic
import org.springframework.data.jpa.repository.JpaRepository

interface MicRepository: JpaRepository<Mic, Long> {
}