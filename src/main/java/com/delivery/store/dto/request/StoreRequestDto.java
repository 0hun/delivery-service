package com.delivery.store.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.delivery.store.domain.StoreEnableStatus;
import com.delivery.store.domain.Store;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class StoreRequestDto {

    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{1,30}$")
    private String name;

    @NotBlank
    @Length(min = 11, max = 13)
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String telephone;

    @NotBlank
    @Length(min = 5, max = 50)
    @Pattern(regexp = "^[\\s\\{0,\\}ㄱ-ㅎ가-힣a-z0-9_-]{5,50}$")
    private String address;

    @NotBlank
    @Length(min = 1, max = 10)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,10}$", message = "이름은 2 ~ 10글자 국문으로 입력하세요")
    private String managerName;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 번호는 xxx-xx-xxxxx 로 입력해주세요")
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
