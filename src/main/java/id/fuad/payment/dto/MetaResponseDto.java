package id.fuad.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class MetaResponseDto {
    @JsonProperty("message")
    String message;

    @JsonProperty("status_code")
    Integer statusCode;

    @JsonProperty("error")
    List<ErrorBagDto> errorBag;

    @JsonProperty("status_code_text")
    String statusCodeText;
}
