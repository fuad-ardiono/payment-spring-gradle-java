package id.fuad.payment.utils.base;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.MetaResponseDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ControllerBaseTest {
    @InjectMocks
    ControllerBase controllerBase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResponseWithMessage() {
        PaymentTypeDto paymentTypeDto = PaymentTypeDto.builder()
                .type("cash")
                .id(1L)
                .build();

        MetaResponseDto metaResponseDto = MetaResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .statusCodeText(HttpStatus.OK.getReasonPhrase())
                .message("ABC DEFG")
                .build();

        BaseResponseDto<PaymentTypeDto> baseResponse= BaseResponseDto.<PaymentTypeDto>builder()
                .data(paymentTypeDto)
                .meta(metaResponseDto)
                .build();

        ResponseEntity<BaseResponseDto<PaymentTypeDto>> response = controllerBase
                .response(paymentTypeDto, HttpStatus.OK, "ABC", "DEFG");

        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, Objects.requireNonNull(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
        Assertions.assertEquals(response.getBody(), baseResponse);
    }

    @Test
    void testResponseWithoutMessage() {
        PaymentTypeDto paymentTypeDto = PaymentTypeDto.builder()
                .type("cash")
                .id(1L)
                .build();

        MetaResponseDto metaResponseDto = MetaResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .statusCodeText(HttpStatus.OK.getReasonPhrase())
                .build();

        BaseResponseDto<PaymentTypeDto> baseResponse= BaseResponseDto.<PaymentTypeDto>builder()
                .data(paymentTypeDto)
                .meta(metaResponseDto)
                .build();

        ResponseEntity<BaseResponseDto<PaymentTypeDto>> response = controllerBase
                .response(paymentTypeDto, HttpStatus.OK);

        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, Objects.requireNonNull(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
        Assertions.assertEquals(response.getBody(), baseResponse);
    }
}
