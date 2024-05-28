package com.wonjung.cono.controller

import com.wonjung.cono.dto.res.ConoResDto
import com.wonjung.cono.service.ConoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/conos")
class ConoController(
    val conoService: ConoService
) {
    /**
     * 키워드로 노래방 검색
     */
    @GetMapping
    fun getConoList(
        @RequestParam(value = "q") query: String,
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Page<ConoResDto>> {
        val conoList = conoService.getConoList(query, pageable)
        return ResponseEntity.ok(conoList)
    }
}