package com.delivery.store.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.delivery.store.model.request.StoreRequestDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class StoreEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telephone;
    private String address;
    private String managerName;
    private String businessNumber;

    @Builder
    public StoreEntity(Long id, String name, String telephone, String address, String managerName,
        String businessNumber) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.managerName = managerName;
        this.businessNumber = businessNumber;
    }

    public void updateInformation(StoreRequestDto storeRequest) {
        this.name = storeRequest.getName();
        this.telephone = storeRequest.getTelephone();
        this.address = storeRequest.getAddress();
        this.managerName = storeRequest.getManagerName();
        this.businessNumber = storeRequest.getBusinessNumber();
    }
}
