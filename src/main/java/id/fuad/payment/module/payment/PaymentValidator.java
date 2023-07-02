package id.fuad.payment.module.payment;

import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.ValidatedCreatePaymentDto;
import id.fuad.payment.repository.masterdata.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentValidator {
    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    ValidatedCreatePaymentDto validateCreatePayment(PaymentDto requestData) throws UnprocessableContentException {
        Optional<PaymentTypeEntity> paymentType = paymentTypeRepository.findById(requestData.getPaymentTypeId());
        ValidatedCreatePaymentDto validatedCreatePaymentDto = ValidatedCreatePaymentDto.builder().build();

        if (paymentType.isPresent()) {
            validatedCreatePaymentDto.setPaymentType(paymentType.get());
        } else {
            throw new UnprocessableContentException("Payment type not found");
        }

        return validatedCreatePaymentDto;
    }
}
