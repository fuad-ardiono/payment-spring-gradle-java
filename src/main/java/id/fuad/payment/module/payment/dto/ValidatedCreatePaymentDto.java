package id.fuad.payment.module.payment.dto;

import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatedCreatePaymentDto {
    PaymentTypeEntity paymentType;
}
