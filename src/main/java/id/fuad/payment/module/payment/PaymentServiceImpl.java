package id.fuad.payment.module.payment;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.payment.dto.PaymentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaginationDto<List<PaymentDto>> getPayment(Integer page, Integer perPage) {
        return null;
    }
}
