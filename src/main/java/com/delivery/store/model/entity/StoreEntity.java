package com.delivery.store.model.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "store")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
