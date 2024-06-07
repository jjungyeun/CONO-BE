package com.wonjung.cono.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.wonjung.cono.dto.req.ConoCreateReqDto
import com.wonjung.cono.dto.req.FeeReqDto
import com.wonjung.cono.dto.req.LocationReqDto
import com.wonjung.cono.entity.AddressInfo
import com.wonjung.cono.entity.Cono
import com.wonjung.cono.repo.ConoRepository
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
    @Autowired val conoRepository: ConoRepository
) {
    @Test
    fun getConoList() {
        val conoName = "판타스틱코인노래연습장 분당오리역점"
        val conoAddress = "경기 성남시 분당구 구미로9번길 17 광림빌딩 3층"
        val cono = Cono(conoName, AddressInfo(conoAddress, BigDecimal(127.1098975),BigDecimal(37.3398461)))
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
            location = LocationReqDto(BigDecimal(127.1098975),BigDecimal(37.3398461)),
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
}