package id.fuad.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PaginationDto<T> {
    @JsonProperty("pagination_meta")
    PaginationMetaDto paginationMeta;

    @JsonProperty("pagination_data")
    T paginationData;
}
