package com.wonjung.cono.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.wonjung.cono.entity.AddressInfo
import com.wonjung.cono.entity.Cono
import com.wonjung.cono.repo.ConoRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
}