package id.fuad.payment.module.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

@Data
@Builder
@Jacksonized
public class InventoryDto {
    @JsonProperty("item_id")
    Long id;

    @JsonProperty("item_name")
    String itemName;

    @JsonProperty("quantity")
    Integer quantity;

    @JsonProperty("price")
    BigInteger price;
}
