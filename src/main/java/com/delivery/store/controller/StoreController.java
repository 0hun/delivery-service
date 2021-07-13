package com.delivery.store.controller;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.delivery.store.domain.Store;
import com.delivery.store.dto.request.StoreRequestDto;
import com.delivery.store.dto.response.StoreResponseDto;
import com.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{id}")
    public StoreResponseDto find(@PathVariable long id) {
        return storeService.find(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody StoreRequestDto storeRequest) throws URISyntaxException {
        Store store = storeService.create(storeRequest);

        return ResponseEntity.created(new URI("/stores/" + store.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody StoreRequestDto storeRequest) {
        storeService.update(id, storeRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        storeService.delete(id);

        return ResponseEntity.ok().build();
    }
}
