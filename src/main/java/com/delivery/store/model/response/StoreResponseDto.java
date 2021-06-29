package com.delivery.store.model.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.delivery.store.model.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {

    private final long id;
    private final String name;
    private final String telephone;
    private final String address;
    private final String managerName;
    private final String businessNumber;
    private final String createdAt;
    private final String updatedAt;

    public StoreResponseDto(Store entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.telephone = entity.getTelephone();
        this.address = entity.getAddress();
        this.managerName = entity.getManagerName();
        this.businessNumber = entity.getBusinessNumber();
        this.createdAt = localDateTimeToString(entity.getCreatedAt());
        this.updatedAt = localDateTimeToString(entity.getUpdatedAt());
    }

    public String localDateTimeToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        return localDateTime.format(dateTimeFormatter);
    }
}
