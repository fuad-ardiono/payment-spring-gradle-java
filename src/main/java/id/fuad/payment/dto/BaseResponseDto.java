package id.fuad.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BaseResponseDto<T> {
    @JsonProperty("meta")
    MetaResponseDto meta;

    @JsonProperty("data")
    T data;
}

