package id.fuad.payment.module.payment;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    PaginationDto<List<PaymentDto>> getPaymentPagination(Long customerId, Integer page, Integer pageSize);

    PaymentResponseDto getPaymentDetail(Long paymentId) throws NotFoundException;

    PaymentDto createPayment(PaymentDto requestData) throws UnprocessableContentException;

    PaymentDto updatePayment(Long paymentId, PaymentDto requestData) throws UnprocessableContentException;

    PaymentDto deletePayment(Long paymentId) throws UnprocessableContentException;
}
