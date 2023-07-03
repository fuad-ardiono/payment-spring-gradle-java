package id.fuad.payment.module.payment;

import id.fuad.payment.config.constant.IssueConstant;
import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.repository.masterdata.PaymentTypeRepository;
import id.fuad.payment.repository.transactional.PaymentRepository;
import id.fuad.payment.utils.base.ValidationBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class PaymentValidator {
    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Autowired
    PaymentRepository paymentRepository;

    void validatePayment(PaymentDto requestData) throws UnprocessableContentException {
        validatePaymentField(requestData);

        ValidationBase validationBase = new ValidationBase(new ArrayList<>());

        boolean paymentType = paymentTypeRepository.existsById(requestData.getPaymentTypeId());

        if (!paymentType) {
            validationBase.addErrorField("payment_type_id", IssueConstant.NOT_MATCH);
        }

        validationBase.raiseError();
    }

    void validatePayment(Long paymentId, PaymentDto requestData) throws UnprocessableContentException {
        validatePaymentField(requestData);

        ValidationBase validationBase = new ValidationBase(new ArrayList<>());

        boolean payment = paymentRepository.existsById(paymentId);

        if (!payment) {
            validationBase.addErrorPathVariable(":paymentId", IssueConstant.NOT_MATCH);
        }

        boolean paymentType = paymentTypeRepository.existsById(requestData.getPaymentTypeId());

        if (!paymentType) {
            validationBase.addErrorField("payment_type_id", IssueConstant.NOT_MATCH);
        }

        validationBase.raiseError();
    }

    void validatePayment(Long paymentId) throws UnprocessableContentException {
        ValidationBase validationBase = new ValidationBase(new ArrayList<>());

        boolean payment = paymentRepository.existsById(paymentId);

        if (!payment) {
            validationBase.addErrorPathVariable("paymentId", IssueConstant.NOT_MATCH);
        }

        validationBase.raiseError();
    }

    private void validatePaymentField(PaymentDto requestData) throws UnprocessableContentException {
        Optional<Long> paymentTypeId = Optional.ofNullable(requestData.getPaymentTypeId());

        ValidationBase validationBase = new ValidationBase(new ArrayList<>());

        if (paymentTypeId.isEmpty()) {
            validationBase.addErrorField("payment_type_id", IssueConstant.REQUIRED);
        }

        Optional<Long> customerId = Optional.ofNullable(requestData.getCustomerId());

        if (customerId.isEmpty()) {
            validationBase.addErrorField("customer_id", IssueConstant.REQUIRED);
        }

        Optional<BigInteger> amount = Optional.ofNullable(requestData.getAmount());

        if (amount.isEmpty()) {
            validationBase.addErrorField("amount", IssueConstant.REQUIRED);
        }

        validationBase.raiseError();
    }
}
