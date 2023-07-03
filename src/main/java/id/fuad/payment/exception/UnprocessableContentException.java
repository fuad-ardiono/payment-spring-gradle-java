package id.fuad.payment.exception;

import id.fuad.payment.dto.ErrorBagDto;

import java.util.List;

public class UnprocessableContentException extends Exception {
    public List<ErrorBagDto> errorBagDtos;

    public UnprocessableContentException(String message) {
        super(message);
    }

    public UnprocessableContentException(List<ErrorBagDto> errorBagDtos) {
        super("Validation failed");
        this.errorBagDtos = errorBagDtos;
    }
}
