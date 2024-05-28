package com.wonjung.cono.controller

import com.wonjung.cono.dto.res.ConoListResDto
import com.wonjung.cono.service.ConoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/conos")
class ConoController(
    val conoService: ConoService
) {
    /**
     * 노래방 목록 조회
     */
    @GetMapping
    fun getConoList(): ResponseEntity<ConoListResDto> {
        val conoList = conoService.getConoList()
        return ResponseEntity.ok(conoList)
    }
}