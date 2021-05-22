package com.delivery.store.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "store")
public class StoreEntity {

    @Id
    private Long id;
    private String name;
    private String telephone;
    private String address;
    private String managerName;
    private String businessNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public StoreEntity(Long id, String name, String telephone, String address, String managerName,
        String businessNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.managerName = managerName;
        this.businessNumber = businessNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
