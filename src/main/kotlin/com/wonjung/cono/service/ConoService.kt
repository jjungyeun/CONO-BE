package com.wonjung.cono.service

import com.wonjung.cono.dto.res.ConoResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface ConoService {
    fun getConoList(query: String, pageable: Pageable): Page<ConoResDto>
}

@Service
class ConoServiceTestImpl: ConoService {
    override fun getConoList(query: String, pageable: Pageable): Page<ConoResDto> {
        println("ConoServiceTestImpl.getConoList")
        println("query = [${query}], pageable = [${pageable}]")
        return PageImpl(
            listOf(
                ConoResDto(1L, "판타스틱 코인노래방 미금역점", "성남시 분당구 XX-XX"),
                ConoResDto(2L, "슈퍼스타 코인노래방 미금역점", "성남시 분당구 XX-XX"),
            ),
            PageRequest.of(1, 10), 2
        )
    }

}