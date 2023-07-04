package id.fuad.payment.modules.payment;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.dto.PaginationMetaDto;
import id.fuad.payment.module.payment.PaymentController;
import id.fuad.payment.module.payment.PaymentService;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.PaymentResponseDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentControllerTest {
    @Mock
    PaymentService paymentService;

    @InjectMocks
    PaymentController paymentController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListPagination() {
        List<PaymentDto> paymentList = new ArrayList<>();
        paymentList.add(
                PaymentDto.builder()
                        .paymentId(1L)
                        .paymentTypeId(1L)
                        .customerId(1L)
                        .amount(new BigInteger("3000"))
                        .build()
        );
        paymentList.add(
                PaymentDto.builder()
                        .paymentId(1L)
                        .paymentTypeId(1L)
                        .customerId(1L)
                        .amount(new BigInteger("4000"))
                        .build()
        );

        PaginationMetaDto paginationMetaDto = PaginationMetaDto.builder()
                .page(2)
                .pageSize(10)
                .totalPage(2)
                .totalRecord(20L)
                .build();

        PaginationDto<List<PaymentDto>> pagination = PaginationDto.<List<PaymentDto>>builder()
                .paginationData(paymentList)
                .paginationMeta(paginationMetaDto)
                .build();

        Mockito.when(paymentService.getPaymentPagination(Mockito.eq(2), Mockito.eq(10)))
                .thenReturn(pagination);

        ResponseEntity<BaseResponseDto<PaginationDto<List<PaymentDto>>>> response = paymentController
                .getPaymentListPagination(2, 10);

        Assertions.assertEquals(paginationMetaDto, Objects.requireNonNull(response.getBody()).getData().getPaginationMeta());
        Assertions.assertEquals(paymentList, Objects.requireNonNull(response.getBody()).getData().getPaginationData());
        Assertions.assertEquals(paymentList.get(0).getPaymentId(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getPaymentId());
        Assertions.assertEquals(paymentList.get(0).getAmount(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getAmount());
        Assertions.assertEquals(paymentList.get(0).getPaymentTypeId(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getPaymentTypeId());
        Assertions.assertEquals(paymentList.get(0).getCustomerId(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getCustomerId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Success get payment list pagination", response.getBody().getMeta().getMessage());
    }

    @Test
    void getPaymentDetail() {
        PaymentResponseDto paymentResponseDto = PaymentResponseDto.builder()
                .paymentType(
                        PaymentTypeDto.builder()
                                .id(1L)
                                .type("cash")
                                .build()
                )
                .amount(new BigInteger("2000"))
                .customerId(1L)
                .id(2L)
                .build();

        Assertions.assertDoesNotThrow(() -> {
            Mockito.when(paymentService.getPaymentDetail(Mockito.eq(9L)))
                    .thenReturn(paymentResponseDto);

            ResponseEntity<BaseResponseDto<PaymentResponseDto>> response = paymentController.getPaymentDetail(9L);

            Assertions.assertEquals(paymentResponseDto, Objects.requireNonNull(response.getBody()).getData());
            Assertions.assertEquals(paymentResponseDto.getId(), Objects.requireNonNull(response.getBody()).getData().getId());
            Assertions.assertEquals(paymentResponseDto.getAmount(), Objects.requireNonNull(response.getBody()).getData().getAmount());
            Assertions.assertEquals(paymentResponseDto.getPaymentType(), Objects.requireNonNull(response.getBody()).getData().getPaymentType());
            Assertions.assertEquals(paymentResponseDto.getCustomerId(), Objects.requireNonNull(response.getBody()).getData().getCustomerId());
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals("Success get detail paymentId 9", response.getBody().getMeta().getMessage());
        });
    }

    @Test
    void testCreatePayment() {
        PaymentDto paymentDto = PaymentDto.builder()
                .paymentId(1L)
                .paymentTypeId(1L)
                .customerId(1L)
                .amount(new BigInteger("3000"))
                .build();

        Assertions.assertDoesNotThrow(() -> {
            Mockito.when(paymentService.createPayment(Mockito.any()))
                    .thenReturn(paymentDto);

            ResponseEntity<BaseResponseDto<PaymentDto>> response = paymentController.createPayment(paymentDto);

            Assertions.assertEquals(paymentDto, Objects.requireNonNull(response.getBody()).getData());
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
            Assertions.assertEquals("Success create payment", response.getBody().getMeta().getMessage());
        });
    }

    @Test
    void testUpdatePayment() {
        PaymentDto paymentDto = PaymentDto.builder()
                .paymentId(1L)
                .paymentTypeId(1L)
                .customerId(1L)
                .amount(new BigInteger("3000"))
                .build();

        Assertions.assertDoesNotThrow(() -> {
            Long paymentId = 10L;
            Mockito.when(paymentService.updatePayment(Mockito.eq(paymentId), Mockito.any()))
                    .thenReturn(paymentDto);

            ResponseEntity<BaseResponseDto<PaymentDto>> response = paymentController.updatePayment(paymentDto, paymentId);

            Assertions.assertEquals(paymentDto, Objects.requireNonNull(response.getBody()).getData());
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals("Success update payment", response.getBody().getMeta().getMessage());
        });
    }

    @Test
    void testDeletePayment() {
        PaymentDto paymentDto = PaymentDto.builder()
                .paymentId(1L)
                .paymentTypeId(1L)
                .customerId(1L)
                .amount(new BigInteger("3000"))
                .build();

        Assertions.assertDoesNotThrow(() -> {
            Long paymentId = 11L;
            Mockito.when(paymentService.deletePayment(Mockito.eq(paymentId)))
                    .thenReturn(paymentDto);

            ResponseEntity<BaseResponseDto<PaymentDto>> response = paymentController.deletePayment(paymentId);

            Assertions.assertEquals(paymentDto, Objects.requireNonNull(response.getBody()).getData());
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals("Success delete payment", response.getBody().getMeta().getMessage());
        });
    }
}
