package com.wonjung.cono.service

import com.wonjung.cono.dto.res.ConoListResDto
import com.wonjung.cono.dto.res.ConoResDto
import org.springframework.stereotype.Service

@Service
interface ConoService {
    fun getConoList(): ConoListResDto
}

@Service
class ConoServiceTestImpl: ConoService {
    override fun getConoList(): ConoListResDto {
        return ConoListResDto(
            listOf(
                ConoResDto(1L, "판타스틱 코인노래방 미금역점", "성남시 분당구 XX-XX"),
                ConoResDto(2L, "슈퍼스타 코인노래방 미금역점", "성남시 분당구 XX-XX"),
            )
        )
    }

}