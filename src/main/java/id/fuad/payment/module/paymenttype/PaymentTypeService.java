package id.fuad.payment.module.paymenttype;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;

import java.util.List;

public interface PaymentTypeService {
    PaginationDto<List<PaymentTypeDto>> getPaymentTypePagination(Integer page, Integer pageSize);
}
