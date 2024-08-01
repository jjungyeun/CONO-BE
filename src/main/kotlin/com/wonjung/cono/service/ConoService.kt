package com.wonjung.cono.service

import com.wonjung.cono.dto.req.ConoCreateReqDto
import com.wonjung.cono.dto.res.ConoDetailResDto
import com.wonjung.cono.dto.res.ConoResDto
import com.wonjung.cono.dto.res.LocationResDto
import com.wonjung.cono.entity.AddressInfo
import com.wonjung.cono.entity.Cono
import com.wonjung.cono.entity.Fee
import com.wonjung.cono.entity.Mic
import com.wonjung.cono.repo.ConoRepository
import com.wonjung.cono.repo.FeeRepository
import com.wonjung.cono.repo.MicRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
interface ConoService {
    fun getConoList(query: String, pageable: Pageable): Page<ConoResDto>
    fun createCono(createDto: ConoCreateReqDto): Long
    fun getConoDetail(conoId: Long): ConoDetailResDto
}

@Service
@Transactional(readOnly = true)
class ConoServiceImpl(
    val conoRepository: ConoRepository,
    val feeRepository: FeeRepository,
    val micRepository: MicRepository
): ConoService {
    override fun getConoList(query: String, pageable: Pageable): Page<ConoResDto> {
        return conoRepository.findByNameContaining(query, pageable)
            .map { cono -> ConoResDto(cono.id, cono.name, cono.addressInfo.address) }
    }

    @Transactional
    override fun createCono(createDto: ConoCreateReqDto): Long {
        val cono = conoRepository.save(
            Cono(
                name = createDto.name,
                addressInfo = AddressInfo(
                    address = createDto.address,
                    latitude = createDto.location.lan,
                    longitude = createDto.location.lon
                ),
                phoneNumber = createDto.phoneNumber,
                operatingTime = createDto.operatingTime,
                payTypes = createDto.payTypes,
                roomCount = createDto.roomCount,
                os = createDto.os,
                hasScoreBonus = createDto.hasScoreBonus,
                canControlSound = createDto.canControlSound,
                hasMoneyChanger = createDto.hasMoneyChanger
            )
        )

        createDto.fee?.forEach { fee ->
            feeRepository.save(
                Fee(
                    cono = cono,
                    price = fee.price,
                    feeValue = fee.value,
                    unit = fee.unit
                )
            )
        }

        createDto.micTypes?.forEach { type ->
            micRepository.save(
                Mic(cono = cono, micType = type)
            )
        }

        return cono.id
    }

    override fun getConoDetail(conoId: Long): ConoDetailResDto {
        val resDto: ConoDetailResDto

        val cono = conoRepository.findById(conoId)
            .orElseThrow { NotFoundException() } // TODO: custom Exception 추가
            .also {
                resDto = ConoDetailResDto(
                    id = conoId,
                    updatedDate = it.updatedDatetime.toLocalDate(),
                    name = it.name,
                    address = it.addressInfo.address,
                    location = LocationResDto(it.addressInfo.latitude, it.addressInfo.longitude),
                    operatingTime = it.operatingTime,
                    phoneNumber = it.phoneNumber,
                    payTypes = it.payTypesToString(),
                    roomCount = it.roomCount,
                    os = it.os?.value,
                    hasScoreBonus = it.hasScoreBonus,
                    canControlSound = it.canControlSound,
                    hasMoneyChanger = it.hasMoneyChanger
                )
            }

        micRepository.findAllByCono(cono)
            .map { it.micType.value }
            .also { list ->
                if (list.isNotEmpty()) {
                    resDto.micTypes = list.joinToString(", ")
                }
            }

        feeRepository.findAllByCono(cono)
            .sortedWith(compareBy({ it.unit }, { it.price }))
            .map {
                it.feeToString()
            }
            .also { list ->
                if (list.isNotEmpty()) {
                    resDto.fee = list.joinToString(",\n")
                }
            }

        return resDto
    }
}