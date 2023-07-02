package id.fuad.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class MetaResponseDto {
    @JsonProperty("status_code")
    Integer statusCode;

    @JsonProperty("status_code_text")
    String statusCodeText;
}
