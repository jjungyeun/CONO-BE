package com.wonjung.cono.dto.res

import com.wonjung.cono.dto.SnakeCaseDto
import com.wonjung.cono.type.MicType
import com.wonjung.cono.type.OsType
import com.wonjung.cono.type.PayType
import java.math.BigDecimal

data class ConoResDto (
    val id: Long,
    val name: String,
    val address: String
): SnakeCaseDto()

data class ConoDetailResDto (
    val id: Long,
    var name: String,
    var address: String,
    var location: LocationResDto,
    var operatingTime: String? = null,
    var phoneNumber: String? = null,
    val payTypes: Set<PayType> = mutableSetOf(),
    var roomCount: Int? = null,
    var os: OsType? = null,
    var hasScoreBonus: Boolean? = null,
    var canControlSound: Boolean? = null,
    var hasMoneyChanger: Boolean? = null,
    val micTypes: MutableList<MicType> = mutableListOf(),
    var fee: String? = null
) : SnakeCaseDto()

data class LocationResDto (
    val lan: BigDecimal,
    val lon: BigDecimal
)