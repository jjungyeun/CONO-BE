package com.wonjung.cono.service

import com.wonjung.cono.dto.res.ConoResDto
import com.wonjung.cono.repo.ConoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
interface ConoService {
    fun getConoList(query: String, pageable: Pageable): Page<ConoResDto>
}

@Service
@Transactional(readOnly = true)
class ConoServiceImpl(
    val conoRepository: ConoRepository
): ConoService {
    override fun getConoList(query: String, pageable: Pageable): Page<ConoResDto> {
        return conoRepository.findByNameContaining(query, pageable)
            .map { cono -> ConoResDto(cono.id, cono.name, cono.addressInfo.address) }
    }
}