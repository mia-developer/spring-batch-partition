package org.example.data.expression;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import org.example.data.entity.BasePaymentGatewayHistoryEntity;
import org.example.data.entity.PaymentHistoryEntity;
import org.example.data.entity.enums.PaymentGatewayType;

@Getter
public class PaymentHistoryProjection {
    private Long id;
    private PaymentGatewayType paymentGatewayType;
    private String number;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String promotionCode;

    @QueryProjection
    public PaymentHistoryProjection(
            final PaymentHistoryEntity paymentHistory,
            final BasePaymentGatewayHistoryEntity basePaymentGatewayHistory) {
        if (paymentHistory == null || basePaymentGatewayHistory == null) {
            return;
        }

        this.id = paymentHistory.getId();
        this.paymentGatewayType = paymentHistory.getPaymentGatewayType();
        this.number = paymentHistory.getNumber();
        this.amount = paymentHistory.getAmount();
        this.paymentDate = paymentHistory.getPaymentDate();
        this.promotionCode = basePaymentGatewayHistory.getPromotionCode();
    }
}
