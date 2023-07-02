package id.fuad.payment.module.payment;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.entity.transactional.PaymentEntity;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.PaymentResponseDto;
import id.fuad.payment.module.payment.dto.ValidatedCreatePaymentDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import id.fuad.payment.repository.transactional.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentValidator paymentValidator;

    @Override
    public PaginationDto<List<PaymentDto>> getPaymentPagination(Integer page, Integer perPage) {
        return null;
    }

    @Override
    public PaymentResponseDto getPaymentDetail(Long paymentId) throws NotFoundException {
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        PaymentTypeDto paymentTypeDto = PaymentTypeDto.builder()
                .type(paymentEntity.getPaymentType().getTypeName())
                .id(paymentEntity.getPaymentType().getId())
                .build();

        return PaymentResponseDto.builder()
                .paymentType(paymentTypeDto)
                .customerId(paymentEntity.getCustomerId())
                .amount(paymentEntity.getAmount())
                .build();
    }

    @Override
    public PaymentDto createPayment(PaymentDto requestData) throws UnprocessableContentException {
        ValidatedCreatePaymentDto validatedCreatePayment = paymentValidator.validateCreatePayment(requestData);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(requestData.getAmount());
        paymentEntity.setCustomerId(requestData.getCustomerId());
        paymentEntity.setPaymentType(validatedCreatePayment.getPaymentType());

        PaymentEntity recordPayment = paymentRepository.saveAndFlush(paymentEntity);

        return PaymentDto.builder()
                .amount(recordPayment.getAmount())
                .paymentTypeId(recordPayment.getPaymentType().getId())
                .customerId(recordPayment.getCustomerId())
                .build();
    }

    @Override
    public PaymentDto updatePayment(Long paymentId, PaymentDto requestData) throws UnprocessableContentException {
        ValidatedCreatePaymentDto validatedCreatePayment = paymentValidator.validateCreatePayment(requestData);

        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new UnprocessableContentException("Payment not found"));

        payment.setAmount(requestData.getAmount());
        payment.setPaymentType(validatedCreatePayment.getPaymentType());
        payment.setCustomerId(requestData.getCustomerId());

        PaymentEntity updatedPayment = paymentRepository.saveAndFlush(payment);

        return PaymentDto.builder()
                .amount(updatedPayment.getAmount())
                .paymentTypeId(updatedPayment.getPaymentType().getId())
                .customerId(updatedPayment.getCustomerId())
                .build();
    }
}
