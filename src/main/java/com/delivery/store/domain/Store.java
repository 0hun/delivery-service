package com.delivery.store.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.delivery.store.dto.request.StoreRequestDto;
import com.delivery.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String name;
    private String telephone;
    private String address;
    private String managerName;
    private String businessNumber;
    @Enumerated(EnumType.STRING)
    private StoreEnableStatus storeEnableStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Store(Long id, String name, String telephone, String address, String managerName,
        String businessNumber, StoreEnableStatus storeEnableStatus, User user) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.managerName = managerName;
        this.businessNumber = businessNumber;
        this.storeEnableStatus = storeEnableStatus;
        this.user = user;
    }

    public void updateInformation(StoreRequestDto storeRequest) {
        this.name = storeRequest.getName();
        this.telephone = storeRequest.getTelephone();
        this.address = storeRequest.getAddress();
        this.managerName = storeRequest.getManagerName();
        this.businessNumber = storeRequest.getBusinessNumber();
    }

    public void disableStore() {
        this.storeEnableStatus = StoreEnableStatus.DISABLED;
    }
}
