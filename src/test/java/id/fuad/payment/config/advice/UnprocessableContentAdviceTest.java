package id.fuad.payment.config.advice;

import id.fuad.payment.config.constant.IssueConstant;
import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.MetaResponseDto;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import id.fuad.payment.utils.base.ValidationBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

public class UnprocessableContentAdviceTest {
    @InjectMocks
    UnprocessableContentAdvice unprocessableContentAdvice;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResponseShouldHaveUnprocessableEntityHttpStatus() {
        MetaResponseDto metaResponseDto = MetaResponseDto.builder()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .statusCodeText(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message("ABC DEFG")
                .errorBag(new ArrayList<>())
                .build();

        BaseResponseDto<?> baseResponse= BaseResponseDto.<PaymentTypeDto>builder()
                .data(null)
                .meta(metaResponseDto)
                .build();

        UnprocessableContentException unprocessableContentException = new UnprocessableContentException("ABC DEFG");

        WebRequest request = Mockito.mock(WebRequest.class);

        ResponseEntity<BaseResponseDto<?>> handlerResponse = unprocessableContentAdvice
                .unprocessableContentExceptionHandling(unprocessableContentException, request);

        Assertions.assertEquals(baseResponse, handlerResponse.getBody());
    }

    @Test
    void testResponseWithErrorBag() {
        ValidationBase validationBase = new ValidationBase(new ArrayList<>());
        validationBase.addErrorField("test", IssueConstant.REQUIRED);

        MetaResponseDto metaResponseDto = MetaResponseDto.builder()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .statusCodeText(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message("Validation failed")
                .errorBag(validationBase.errorBagDtos())
                .build();

        BaseResponseDto<?> baseResponse= BaseResponseDto.<PaymentTypeDto>builder()
                .data(null)
                .meta(metaResponseDto)
                .build();

        UnprocessableContentException unprocessableContentException = new UnprocessableContentException(validationBase.errorBagDtos());

        WebRequest request = Mockito.mock(WebRequest.class);

        ResponseEntity<BaseResponseDto<?>> handlerResponse = unprocessableContentAdvice
                .unprocessableContentExceptionHandling(unprocessableContentException, request);

        Assertions.assertEquals(baseResponse, handlerResponse.getBody());
    }
}
