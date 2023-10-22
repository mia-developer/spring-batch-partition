package org.example.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BasePaymentGatewayHistoryEntity {

    @Column(nullable = false)
    private String number;

    @Column
    private String promotionCode;
}
