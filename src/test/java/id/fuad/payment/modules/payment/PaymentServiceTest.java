package id.fuad.payment.modules.payment;

import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import id.fuad.payment.entity.transactional.PaymentEntity;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.module.payment.PaymentServiceImpl;
import id.fuad.payment.module.payment.PaymentValidator;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.PaymentResponseDto;
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
import java.util.Optional;

public class PaymentServiceTest {
    @Mock
    PaymentRepository paymentRepository;

    @Mock
    PaymentTypeRepository paymentTypeRepository;

    @Mock
    PaymentValidator paymentValidator;

    @InjectMocks
    PaymentServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPaymentDetail() {
        PaymentTypeEntity paymentType = Mockito.mock(PaymentTypeEntity.class);
        paymentType.setId(2L);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentType(paymentType);
        paymentEntity.setAmount(new BigInteger("1000"));
        paymentEntity.setCustomerId(1L);
        paymentEntity.setId(2L);

        Mockito.when(paymentRepository.findById(Mockito.any())).thenReturn(Optional.of(paymentEntity));

        Assertions.assertDoesNotThrow(() -> {
            PaymentResponseDto response = service.getPaymentDetail(1L);

            Assertions.assertEquals(paymentEntity.getId(), response.getId());
            Assertions.assertEquals(paymentEntity.getPaymentType().getId(), response.getPaymentType().getId());
            Assertions.assertEquals(paymentEntity.getAmount(), response.getAmount());
            Assertions.assertEquals(paymentEntity.getCustomerId(), response.getCustomerId());
        });
    }

    @Test
    void testGetPaymentDetailNull() {
        Mockito.when(paymentRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            service.getPaymentDetail(1L);
        });

        Assertions.assertEquals("Payment not found", exception.getMessage());
    }

    @Test
    void testCreatePayment() {
        PaymentDto paymentDto = PaymentDto.builder()
                .paymentTypeId(2L)
                .customerId(1L)
                .amount(new BigInteger("2000"))
                .build();

        PaymentTypeEntity paymentType = Mockito.mock(PaymentTypeEntity.class);
        paymentType.setId(2L);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentType(paymentType);
        paymentEntity.setAmount(new BigInteger("2000"));
        paymentEntity.setCustomerId(1L);
        paymentEntity.setId(99L);

        Assertions.assertDoesNotThrow(() -> {
            Mockito.doNothing().when(paymentValidator).validatePayment(Mockito.any(PaymentDto.class));

            Mockito.when(paymentRepository.saveAndFlush(Mockito.any())).thenReturn(paymentEntity);

            PaymentDto response = service.createPayment(paymentDto);

            Assertions.assertEquals(paymentEntity.getId(), response.getPaymentId());
            Assertions.assertEquals(paymentEntity.getPaymentType().getId(), response.getPaymentTypeId());
            Assertions.assertEquals(paymentEntity.getAmount(), response.getAmount());
            Assertions.assertEquals(paymentEntity.getCustomerId(), response.getCustomerId());
        });
    }

    @Test
    void testUpdatePayment() {
        Long paymentId = 20L;
        Long paymentTypeId = 2L;

        PaymentDto paymentDto = PaymentDto.builder()
                .paymentTypeId(paymentTypeId)
                .customerId(1L)
                .amount(new BigInteger("4000"))
                .build();

        PaymentTypeEntity paymentType = Mockito.mock(PaymentTypeEntity.class);
        paymentType.setId(paymentTypeId);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentType(paymentType);
        paymentEntity.setAmount(new BigInteger("4000"));
        paymentEntity.setCustomerId(1L);
        paymentEntity.setId(99L);

        Assertions.assertDoesNotThrow(() -> {

            Mockito.doNothing().when(paymentValidator).validatePayment(Mockito.eq(paymentId), Mockito.any());

            Mockito.when(paymentTypeRepository.findFirstById(Mockito.eq(paymentTypeId))).thenReturn(paymentType);
            Mockito.when(paymentRepository.findFirstById(Mockito.eq(paymentId))).thenReturn(paymentEntity);

            Mockito.when(paymentRepository.saveAndFlush(Mockito.any())).thenReturn(paymentEntity);

            PaymentDto response = service.updatePayment(paymentId, paymentDto);

            Assertions.assertEquals(paymentEntity.getId(), response.getPaymentId());
            Assertions.assertEquals(paymentEntity.getPaymentType().getId(), response.getPaymentTypeId());
            Assertions.assertEquals(paymentEntity.getAmount(), response.getAmount());
            Assertions.assertEquals(paymentEntity.getCustomerId(), response.getCustomerId());
        });
    }

    @Test
    void testDeletePayment() {
        Long paymentId = 50L;
        Long paymentTypeId = 101L;

        PaymentTypeEntity paymentTypeEntity = Mockito.mock(PaymentTypeEntity.class);
        paymentTypeEntity.setId(paymentTypeId);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(paymentId);
        paymentEntity.setPaymentType(paymentTypeEntity);


        Assertions.assertDoesNotThrow(() -> {
            Mockito.doNothing().when(paymentValidator).validatePayment(Mockito.any(Long.class));

            Mockito.when(paymentRepository.findFirstById(Mockito.eq(paymentId))).thenReturn(paymentEntity);
            Mockito.doNothing().when(paymentRepository).delete(Mockito.eq(paymentEntity));

            PaymentDto response = service.deletePayment(paymentId);

            Assertions.assertEquals(paymentEntity.getId(), response.getPaymentId());
        });
    }
}