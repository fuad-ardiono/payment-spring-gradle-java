package id.fuad.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PaginationMetaDto {
    @JsonProperty("page_size")
    Integer pageSize;

    @JsonProperty("page")
    Integer page;

    @JsonProperty("total_page")
    Integer totalPage;

    @JsonProperty("total_record")
    Long totalRecord;
}
