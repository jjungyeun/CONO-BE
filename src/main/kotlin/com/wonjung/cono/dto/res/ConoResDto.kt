package com.wonjung.cono.dto.res

import com.wonjung.cono.dto.SnakeCaseDto
import java.math.BigDecimal

data class ConoResDto (
    val id: Long,
    val name: String,
    val address: String
): SnakeCaseDto()

data class ConoDetailResDto(
    val id: Long,
    var name: String,
    var address: String,
    var location: LocationResDto,
    var operatingTime: String? = null,
    var phoneNumber: String? = null,
    var payTypes: String? = null,
    var roomCount: Int? = null,
    var os: String? = null,
    var hasScoreBonus: Boolean? = null,
    var canControlSound: Boolean? = null,
    var hasMoneyChanger: Boolean? = null,
    var micTypes: String? = null,
    var fee: String? = null
) : SnakeCaseDto()

data class LocationResDto (
    val lan: BigDecimal,
    val lon: BigDecimal
)