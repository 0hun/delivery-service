package com.delivery.store.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.delivery.common.exception.ExceptionController;
import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.Store;
import com.delivery.store.model.request.StoreRequestDto;
import com.delivery.store.model.response.StoreResponseDto;
import com.delivery.store.service.StoreService;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @InjectMocks
    private StoreController storeController;

    @Mock
    private StoreService storeService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(storeController)
            .setControllerAdvice(new ExceptionController())
            .build();
    }

    @DisplayName("store_단건_조회")
    @Test
    @WithMockUser
    void find() throws Exception {
        // given
        StoreResponseDto store = new StoreResponseDto(Store.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build());

        given(storeService.find(1L)).willReturn(store);

        // when, then
        String url = "/stores/1";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("123-33-12345")));
    }

    @DisplayName("store_단건_조회_실패")
    @Test
    void findFailure() throws Exception {
        // given
        given(storeService.find(1L)).willThrow(NoSuchElementException.class);

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.get(url)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @DisplayName("store_단건_생성")
    @Test
    @WithMockUser
    void create() throws Exception {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();

        Store store = Store.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();

        given(storeService.create(storeRequest)).willReturn(store);

        // when, then
        String url = "/stores";

        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(header().string("location", "/stores/1"))
            .andExpect(status().isCreated());

        then(storeService).should().create(eq(storeRequest));
    }

    @DisplayName("store_단건_생성_실패")
    @Test
    void createFailure() throws Exception {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .build();

        // when, then
        String url = "/stores";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("store_단건_수정")
    @Test
    @WithMockUser
    void update() throws Exception {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();

        willDoNothing().given(storeService).update(1L, storeRequest);

        // when, then
        String url = "/stores/1";

        mockMvc.perform(MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(status().isOk());

        then(storeService).should().update(eq(1L), eq(storeRequest));
    }

    @DisplayName("store_단건_수정_실패_400")
    @Test
    void updateFailure400() throws Exception {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .build();

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("store_단건_수정_실패_404")
    @Test
    void updateFailure404() throws Exception {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();

        willThrow(NoSuchElementException.class).given(storeService).update(1L, storeRequest);

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(storeRequest)))
            .andExpect(status().isNotFound());

        then(storeService).should().update(eq(1L), eq(storeRequest));
    }

    @DisplayName("store_단건_비활성화")
    @Test
    @WithMockUser
    void delete() throws Exception {
        // given
        willDoNothing().given(storeService).delete(1L);

        // when, then
        String url = "/stores/1";

        mockMvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        then(storeService).should().delete(eq(1L));
    }

    @DisplayName("store_단건_비활성화_실패")
    @Test
    void deleteFailure() throws Exception {
        // given
        willThrow(NoSuchElementException.class).given(storeService).delete(1L);

        // when, then
        String url = "/stores/1";
        mockMvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
