package id.fuad.payment.module.payment;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.payment.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaginationDto<List<PaymentDto>> getPayment(Integer page, Integer perPage);
}
