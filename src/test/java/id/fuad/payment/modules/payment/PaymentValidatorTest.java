package id.fuad.payment.modules.payment;

import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.payment.PaymentValidator;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.repository.masterdata.PaymentTypeRepository;
import id.fuad.payment.repository.transactional.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

public class PaymentValidatorTest {
    @Mock
    PaymentTypeRepository paymentTypeRepository;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentValidator paymentValidator;

    PaymentDto paymentDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        paymentDto = PaymentDto
                .builder()
                .paymentId(2L)
                .paymentTypeId(1L)
                .customerId(1L)
                .amount(new BigInteger("4000"))
                .build();
    }

    @Test
    void testValidatePaymentFieldShouldBeAllRequired() {
        paymentDto.setPaymentTypeId(null);
        paymentDto.setCustomerId(null);
        paymentDto.setAmount(null);

        UnprocessableContentException exception = Assertions.assertThrows(UnprocessableContentException.class, () ->
                paymentValidator.validatePayment(paymentDto)
        );

        Assertions.assertEquals(3, exception.errorBagDtos.size());
    }

    @Test
    void testValidatePaymentCreate() {
        Mockito.when(paymentTypeRepository.existsById(Mockito.eq(paymentDto.getPaymentTypeId())))
                .thenReturn(false);

        UnprocessableContentException exception = Assertions.assertThrows(UnprocessableContentException.class, () ->
                paymentValidator.validatePayment(paymentDto)
        );

        Assertions.assertEquals(1, exception.errorBagDtos.size());
    }

    @Test
    void testValidatePaymentUpdate() {
        Mockito.when(paymentRepository.existsById(Mockito.eq(paymentDto.getPaymentId())))
                .thenReturn(false);

        Mockito.when(paymentTypeRepository.existsById(Mockito.eq(paymentDto.getPaymentTypeId())))
                .thenReturn(false);

        UnprocessableContentException exception = Assertions.assertThrows(UnprocessableContentException.class, () ->
                paymentValidator.validatePayment(paymentDto.getPaymentId(), paymentDto)
        );

        Assertions.assertEquals(2, exception.errorBagDtos.size());
    }

    @Test
    void testValidatePaymentDelete() {
        Mockito.when(paymentRepository.existsById(Mockito.eq(paymentDto.getPaymentId())))
                .thenReturn(false);

        UnprocessableContentException exception = Assertions.assertThrows(UnprocessableContentException.class, () ->
                paymentValidator.validatePayment(paymentDto.getPaymentId())
        );

        Assertions.assertEquals(1, exception.errorBagDtos.size());
    }
}
