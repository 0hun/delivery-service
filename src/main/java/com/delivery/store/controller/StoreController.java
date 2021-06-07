package com.delivery.store.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.request.StoreRequestDto;
import com.delivery.store.model.response.StoreResponseDto;
import com.delivery.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public StoreResponseDto find(@PathVariable long storeId) {
        return storeService.find(storeId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StoreRequestDto storeRequest) throws URISyntaxException {
        StoreEntity store = storeService.create(storeRequest);
        return ResponseEntity.created(new URI("/stores/" + store.getId())).build();
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<?> update(@PathVariable long storeId, @RequestBody StoreRequestDto storeRequest) {
        storeService.update(storeId, storeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> delete(@PathVariable long storeId) {
        storeService.delete(storeId);
        return ResponseEntity.ok().build();
    }
}
