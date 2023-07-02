package id.fuad.payment.utils.base;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.MetaResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ControllerBase {
    public <T> ResponseEntity<BaseResponseDto<T>> response(T data, HttpStatus httpStatus) {
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        MetaResponseDto metaResponse = MetaResponseDto.builder()
                .statusCode(httpStatus.value())
                .statusCodeText(httpStatus.getReasonPhrase())
                .build();

        BaseResponseDto<T> baseResponse = BaseResponseDto.<T>builder()
                .data(data)
                .meta(metaResponse)
                .build();

        return new ResponseEntity<>(baseResponse, header, httpStatus);
    }
}
