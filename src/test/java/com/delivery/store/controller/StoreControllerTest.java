package com.delivery.store.controller;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.request.StoreRequestDto;
import com.delivery.store.model.response.StoreResponseDto;
import com.delivery.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @DisplayName("store_단건_조회")
    @Test
    void findStore() throws Exception {
        String url = "/store/1";
        StoreResponseDto store = new StoreResponseDto(StoreEntity.builder()
                .id(1L)
                .name("곱돌이네")
                .telephone("02-1234-5678")
                .address("서울 송파구 송파1로 27")
                .managerName("황윤호")
                .businessNumber("123123933")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        given(storeService.findStore(1L)).willReturn(store);

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("123123933")));
    }

    @DisplayName("store_단건_생성")
    @Test
    void createStore() throws Exception {
        String url = "/store";
        StoreRequestDto storeRequest = StoreRequestDto.builder()
                .name("곱돌이네")
                .telephone("02-1234-5678")
                .address("서울 송파구 송파1로 27")
                .managerName("황윤호")
                .businessNumber("123123933")
                .build();

        StoreEntity store = StoreEntity.builder()
                .id(1L)
                .name("곱돌이네")
                .telephone("02-1234-5678")
                .address("서울 송파구 송파1로 27")
                .managerName("황윤호")
                .businessNumber("123123933")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(storeService.createStore(storeRequest)).willReturn(store);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"곱돌이네\", \"telephone\":\"02-1234-5678\", \"address\":\"서울 송파구 송파1로 27\", " +
                        "\"managerName\":\"황윤호\", \"businessNumber\":\"123123933\"}"))
                .andExpect(header().string("location", "/store/1"))
                .andExpect(status().isCreated());

        verify(storeService).createStore(eq(storeRequest));
    }
}
