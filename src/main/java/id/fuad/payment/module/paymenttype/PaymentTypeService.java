package id.fuad.payment.module.paymenttype;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.paymenttype.dto.PaymentMethodDto;

import java.util.List;

public interface PaymentTypeService {
    PaginationDto<List<PaymentMethodDto>> getPaymentMethod(Integer page, Integer pageSize);
}
