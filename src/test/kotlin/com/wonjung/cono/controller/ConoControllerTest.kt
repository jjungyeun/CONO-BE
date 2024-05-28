package com.wonjung.cono.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class ConoControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {
    @Test
    fun getConoList() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/conos?q=미금")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].id").value(1))
            .andExpect(jsonPath("$.content[0].name").value("판타스틱 코인노래방 미금역점"))
            .andExpect(jsonPath("$.content[0].address").value("성남시 분당구 XX-XX"))
            .andExpect(jsonPath("$.content[1].id").value(2))
            .andExpect(jsonPath("$.content[1].name").value("슈퍼스타 코인노래방 미금역점"))
            .andExpect(jsonPath("$.content[1].address").value("성남시 분당구 XX-XX"))
            .andDo(print())
    }
}