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
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public StoreResponseDto findStore(@PathVariable Long storeId) {
        return storeService.findStore(storeId);
    }

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreRequestDto storeRequest) throws URISyntaxException {
        StoreEntity store = storeService.createStore(storeRequest);
        return ResponseEntity.created(new URI("/stores/" + store.getId())).body("{}");
    }

    @PutMapping("/{storeId}")
    public StoreResponseDto updateStore(@PathVariable Long storeId, @RequestBody StoreRequestDto storeRequest) throws URISyntaxException {
        return storeService.updateStore(storeId, storeRequest);
    }
}
