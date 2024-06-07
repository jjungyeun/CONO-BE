package com.wonjung.cono.dto.req

import com.wonjung.cono.dto.SnakeCaseDto
import com.wonjung.cono.type.FeeUnit
import com.wonjung.cono.type.MicType
import com.wonjung.cono.type.OsType
import com.wonjung.cono.type.PayType
import java.math.BigDecimal

data class ConoCreateReqDto (
    val name: String,
    val address: String,
    val location: LocationReqDto,
    val operatingTime: String?,
    val phoneNumber: String?,
    val payTypes: List<PayType>?,
    val roomCount: Int?,
    val os: OsType?,
    val hasScoreBonus: Boolean?,
    val canControlSound: Boolean?,
    val hasMoneyChanger: Boolean?,
    val micTypes: List<MicType>?,
    val fee: List<FeeReqDto>?
): SnakeCaseDto()

data class LocationReqDto(
    val lan: BigDecimal,
    val lon: BigDecimal
)

data class FeeReqDto(
    val price: Int,
    val value: Int,
    val unit: FeeUnit
)