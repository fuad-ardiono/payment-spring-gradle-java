package id.fuad.payment.module.paymenttype.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PaymentTypeDto {
    @JsonProperty("payment_type_id")
    Long id;

    @JsonProperty("type_name")
    String type;
}
