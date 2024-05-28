package com.wonjung.cono.dto.res

import com.wonjung.cono.dto.SnakeCaseDto

data class ConoResDto (
    val id: Long,
    val name: String,
    val address: String
): SnakeCaseDto()

data class ConoListResDto (
    val conos: List<ConoResDto> = mutableListOf()
): SnakeCaseDto()
