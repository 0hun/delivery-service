package com.delivery.store.controller;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.request.StoreRequestDto;

import org.springframework.http.HttpStatus;
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
    public StoreResponseDto find(@PathVariable Long storeId) {
        return storeService.find(storeId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StoreRequestDto storeRequest) throws URISyntaxException {
        StoreEntity store = storeService.create(storeRequest);
        return ResponseEntity.created(new URI("/stores/" + store.getId())).build();
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<?> update(@PathVariable Long storeId, @RequestBody StoreRequestDto storeRequest) {
        storeService.update(storeId, storeRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> delete(@PathVariable Long storeId) {
        storeService.delete(storeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
