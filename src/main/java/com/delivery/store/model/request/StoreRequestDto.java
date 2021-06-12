package com.delivery.store.model.request;

import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.Store;

import lombok.*;

@Getter
@EqualsAndHashCode
public class StoreRequestDto {

    private String name;
    private String telephone;
    private String address;
    private String managerName;
    private String businessNumber;

    @Builder
    public StoreRequestDto(String name, String telephone, String address, String managerName, String businessNumber) {
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.managerName = managerName;
        this.businessNumber = businessNumber;
    }

    public Store toEntity() {
        return Store.builder()
            .name(name)
            .telephone(telephone)
            .address(address)
            .managerName(managerName)
            .businessNumber(businessNumber)
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();
    }
}
