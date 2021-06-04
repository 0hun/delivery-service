package com.delivery.store.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.request.StoreRequestDto;
import com.delivery.store.model.response.StoreResponseDto;
import com.delivery.store.service.StoreService;
import com.google.gson.Gson;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @DisplayName("store_단건_조회")
    @Test
    void findStore() throws Exception {
        // given
        StoreResponseDto store = new StoreResponseDto(StoreEntity.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build());

        given(storeService.findStore(1L)).willReturn(store);

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.get(url)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("123123933")));
    }

    @DisplayName("store_단건_생성")
    @Test
    void createStore() throws Exception {
        // given
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
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();

        given(storeService.createStore(storeRequest)).willReturn(store);

        // when, then
        String url = "/stores";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(header().string("location", "/stores/1"))
            .andExpect(status().isCreated());

        then(storeService).should().createStore(eq(storeRequest));
    }

    @DisplayName("store_단건_수정")
    @Test
    void updateStore() throws Exception {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();

        willDoNothing().given(storeService).updateStore(1L, storeRequest);

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(status().isOk());

        then(storeService).should().updateStore(eq(1L), eq(storeRequest));
    }

    @DisplayName("store_단건_비활성화")
    @Test
    void deleteStore() throws Exception {
        // given
        willDoNothing().given(storeService).deleteStore(1L);

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        then(storeService).should().deleteStore(eq(1L));
    }
}
