package com.wonjung.cono.dto.req

import com.wonjung.cono.annotation.NullOrNotBlank
import com.wonjung.cono.dto.SnakeCaseDto
import com.wonjung.cono.type.FeeUnit
import com.wonjung.cono.type.MicType
import com.wonjung.cono.type.OsType
import com.wonjung.cono.type.PayType
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class ConoCreateReqDto (
    @field:NotBlank @field:Size(max = 100)
    val name: String,
    @field:NotBlank @field:Size(max = 255)
    val address: String,
    @field:NotNull @field:Valid
    val location: LocationReqDto,
    @field:NullOrNotBlank @field:Size(max = 255)
    val operatingTime: String?,
    @field:Pattern(regexp = "^\\d{2,4}-\\d{3,4}-\\d{4}$")
    val phoneNumber: String?,
    val payTypes: List<PayType>?,
    @field:Min(1) @field:Max(99)
    val roomCount: Int?,
    val os: OsType?,
    val hasScoreBonus: Boolean?,
    val canControlSound: Boolean?,
    val hasMoneyChanger: Boolean?,
    val micTypes: List<MicType>?,
    @field:Valid
    val fee: List<FeeReqDto>?
): SnakeCaseDto()

data class LocationReqDto(
    @field:NotNull @field:Min(-90) @field:Max(90)
    val lan: BigDecimal,
    @field:NotNull @field:Min(-180) @field:Max(180)
    val lon: BigDecimal
)

data class FeeReqDto(
    @field:NotNull @field:Min(1)
    val price: Int,
    @field:NotNull @field:Min(1)
    val value: Int,
    @field:NotNull
    val unit: FeeUnit
)