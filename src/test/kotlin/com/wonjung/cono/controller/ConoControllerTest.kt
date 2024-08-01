package com.wonjung.cono.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.wonjung.cono.dto.req.ConoCreateReqDto
import com.wonjung.cono.dto.req.FeeReqDto
import com.wonjung.cono.dto.req.LocationReqDto
import com.wonjung.cono.entity.AddressInfo
import com.wonjung.cono.entity.Cono
import com.wonjung.cono.entity.Fee
import com.wonjung.cono.entity.Mic
import com.wonjung.cono.repo.ConoRepository
import com.wonjung.cono.repo.FeeRepository
import com.wonjung.cono.repo.MicRepository
import com.wonjung.cono.type.FeeUnit
import com.wonjung.cono.type.MicType
import com.wonjung.cono.type.OsType
import com.wonjung.cono.type.PayType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ConoControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val conoRepository: ConoRepository,
    @Autowired val feeRepository: FeeRepository,
    @Autowired val micRepository: MicRepository
) {
    @Test
    fun getConoList() {
        val conoName = "판타스틱코인노래연습장 분당오리역점"
        val conoAddress = "경기 성남시 분당구 구미로9번길 17 광림빌딩 3층"
        val cono = Cono(conoName, AddressInfo(conoAddress, BigDecimal("37.3398461"),BigDecimal("127.1098975")))
        conoRepository.save(cono)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/conos?q=오리")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].id").value(cono.id))
            .andExpect(jsonPath("$.content[0].name").value(conoName))
            .andExpect(jsonPath("$.content[0].address").value(conoAddress))
            .andDo(print())
    }

    @Test
    fun createCono() {
        val requestDto = ConoCreateReqDto(
            name = "판타스틱코인노래연습장 분당오리역점",
            address = "경기 성남시 분당구 구미로9번길 17 광림빌딩 3층",
            location = LocationReqDto(BigDecimal("37.3398461"),BigDecimal("127.1098975")),
            operatingTime = null,
            phoneNumber = "010-0000-0000",
            payTypes = listOf(PayType.CARD, PayType.CASH),
            roomCount = 12,
            os = OsType.TJ,
            hasScoreBonus = true,
            canControlSound = false,
            hasMoneyChanger = null,
            micTypes = listOf(MicType.WIRELESS, MicType.STAND),
            fee = listOf(
                FeeReqDto(1000, 2, FeeUnit.SONG)
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/conos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        )
            .andExpect(status().isCreated)
            .andDo(print())

        Assertions.assertEquals(1, conoRepository.count())
    }

    @Test
    fun cannotCreateCono() {
        val requestDto = ConoCreateReqDto(
            name = "판타스틱코인노래연습장 분당오리역점",
            address = "경기 성남시 분당구 구미로9번길 17 광림빌딩 3층",
            location = LocationReqDto(BigDecimal("37.3398461"),BigDecimal("127.1098975")),
            operatingTime = null,
            phoneNumber = "1-123-1234",
            payTypes = null,
            roomCount = 10,
            os = null,
            hasScoreBonus = null,
            canControlSound = null,
            hasMoneyChanger = null,
            micTypes = null,
            fee = listOf(
                FeeReqDto(1000, 1, FeeUnit.SONG)
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/conos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        )
            .andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    fun getConoDetail() {
        val conoName = "판타스틱코인노래연습장 분당오리역점"
        val conoAddress = "경기 성남시 분당구 구미로9번길 17 광림빌딩 3층"
        val lan = "37.3398461"
        val lon = "127.1098975"
        val payTypes = listOf(PayType.CASH, PayType.CARD)
        val cono = Cono(conoName, AddressInfo(conoAddress, BigDecimal(lan),BigDecimal(lon)), null, null, payTypes,
            12, OsType.TJ)
        conoRepository.save(cono)

        feeRepository.saveAll(
            listOf(
                Fee(cono, 3000, 7, FeeUnit.SONG),
                Fee(cono, 10000, 1, FeeUnit.HOUR),
                Fee(cono, 5000, 30, FeeUnit.MINUTE),
                Fee(cono, 1000, 2, FeeUnit.SONG)
            )
        )

        micRepository.saveAll(
            listOf(
                Mic(cono, MicType.WIRELESS),
                Mic(cono, MicType.STAND)
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/conos/{conoId}", cono.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(cono.id))
            .andExpect(jsonPath("$.updated_date").value(cono.updatedDatetime.toLocalDate().toString()))
            .andExpect(jsonPath("$.name").value(conoName))
            .andExpect(jsonPath("$.address").value(conoAddress))
            .andExpect(jsonPath("$.location.lan").value(lan))
            .andExpect(jsonPath("$.location.lon").value(lon))
            .andExpect(jsonPath("$.operating_time").isEmpty)
            .andExpect(jsonPath("$.phone_number").isEmpty)
            .andExpect(jsonPath("$.pay_types").value("현금, 카드"))
            .andExpect(jsonPath("$.room_count").value(12))
            .andExpect(jsonPath("$.os").value(OsType.TJ.value))
            .andExpect(jsonPath("$.has_score_bonus").isEmpty)
            .andExpect(jsonPath("$.can_control_sound").isEmpty)
            .andExpect(jsonPath("$.has_money_changer").isEmpty)
            .andExpect(jsonPath("$.mic_types").value("무선, 스탠드"))
            .andExpect(jsonPath("$.fee").value("1000원에 2곡,\n3000원에 7곡,\n5000원에 30분,\n10000원에 1시간"))
            .andDo(print())
    }
}