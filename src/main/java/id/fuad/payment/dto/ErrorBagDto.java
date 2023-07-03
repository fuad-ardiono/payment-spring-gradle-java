package id.fuad.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorBagDto {
    @JsonProperty("field")
    String field;

    @JsonProperty("path_variable")
    String pathVariable;

    @JsonProperty("issue")
    String issue;
}
