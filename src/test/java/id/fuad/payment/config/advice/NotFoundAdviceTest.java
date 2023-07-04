package id.fuad.payment.config.advice;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.MetaResponseDto;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public class NotFoundAdviceTest {
    @InjectMocks
    NotFoundAdvice notFoundAdvice;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResponseShouldHaveNotFoundHttpStatus() {
        MetaResponseDto metaResponseDto = MetaResponseDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .statusCodeText(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message("ABC DEFG")
                .build();

        BaseResponseDto<?> baseResponse = BaseResponseDto.<PaymentTypeDto>builder()
                .data(null)
                .meta(metaResponseDto)
                .build();

        NotFoundException notFoundException = new NotFoundException("ABC DEFG");

        WebRequest request = Mockito.mock(WebRequest.class);

        ResponseEntity<BaseResponseDto<?>> handlerResponse = notFoundAdvice.dataNotFoundExceptionHandling(notFoundException, request);

        Assertions.assertEquals(baseResponse, handlerResponse.getBody());
    }
}
