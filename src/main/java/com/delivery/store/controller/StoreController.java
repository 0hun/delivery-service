package com.delivery.store.controller;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.request.StoreRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.delivery.store.model.response.StoreResponseDto;
import com.delivery.store.service.StoreService;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store/{storeId}")
    public StoreResponseDto findStore(@PathVariable Long storeId) {
        return storeService.findStore(storeId);
    }

    @PostMapping("/store")
    public ResponseEntity<?> createStore(@RequestBody  StoreRequestDto storeRequest) throws URISyntaxException {
        StoreEntity store = storeService.createStore(storeRequest);
        return ResponseEntity.created(new URI("/store/" + store.getId())).body("{}");
    }
}
