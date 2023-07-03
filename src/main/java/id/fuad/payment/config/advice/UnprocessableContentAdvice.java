package id.fuad.payment.config.advice;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.MetaResponseDto;
import id.fuad.payment.exception.UnprocessableContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UnprocessableContentAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UnprocessableContentException.class})
    public ResponseEntity<?> unprocessableContentExceptionHandling(UnprocessableContentException exception, WebRequest request) {
        MetaResponseDto metaResponseDto = MetaResponseDto
                .builder()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .statusCodeText(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .errorBag(exception.errorBagDtos.stream().toList())
                .message(exception.getMessage())
                .build();

        BaseResponseDto<?> baseResponseDto = BaseResponseDto.builder()
                .data(null)
                .meta(metaResponseDto)
                .build();

        return new ResponseEntity<>(baseResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
