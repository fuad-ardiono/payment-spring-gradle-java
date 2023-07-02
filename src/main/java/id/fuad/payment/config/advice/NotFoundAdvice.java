package id.fuad.payment.config.advice;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.MetaResponseDto;
import id.fuad.payment.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotFoundAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> dataNotFoundExceptionHandling(Exception exception, WebRequest request) {
        MetaResponseDto metaResponseDto = MetaResponseDto
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .statusCodeText(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();

        BaseResponseDto<?> baseResponseDto = BaseResponseDto.builder()
                .data(null)
                .meta(metaResponseDto)
                .build();

        return new ResponseEntity<>(baseResponseDto, HttpStatus.NOT_FOUND);
    }
}
