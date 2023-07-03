package id.fuad.payment.module.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDto {
    @JsonProperty("payment_id")
    Long paymentId;

    @JsonProperty("amount")
    BigInteger amount;

    @JsonProperty("payment_type_id")
    Long paymentTypeId;

    @JsonProperty("customer_id")
    Long customerId;
}
