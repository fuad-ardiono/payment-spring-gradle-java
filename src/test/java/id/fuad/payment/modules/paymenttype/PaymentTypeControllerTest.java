package id.fuad.payment.modules.paymenttype;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.dto.PaginationMetaDto;
import id.fuad.payment.module.paymenttype.PaymentTypeController;
import id.fuad.payment.module.paymenttype.PaymentTypeService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentTypeControllerTest {
    @Mock
    PaymentTypeService paymentTypeService;

    @InjectMocks
    PaymentTypeController paymentTypeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPaymentTypeListPagination() {
        List<PaymentTypeDto> paymentTypeList = new ArrayList<>();
        paymentTypeList.add(PaymentTypeDto.builder().type("cash").id(1L).build());
        paymentTypeList.add(PaymentTypeDto.builder().type("invoice").id(2L).build());

        PaginationMetaDto paginationMetaDto = PaginationMetaDto.builder()
                .page(2)
                .pageSize(10)
                .totalPage(2)
                .totalRecord(20L)
                .build();

        PaginationDto<List<PaymentTypeDto>> pagination = PaginationDto.<List<PaymentTypeDto>>builder()
                .paginationData(paymentTypeList)
                .paginationMeta(paginationMetaDto)
                .build();

        Mockito.when(paymentTypeService.getPaymentTypePagination(Mockito.eq(2), Mockito.eq(10)))
                .thenReturn(pagination);

        ResponseEntity<BaseResponseDto<PaginationDto<List<PaymentTypeDto>>>> response = paymentTypeController
                .getPaymentTypeListPagination(2, 10);

        Assertions.assertEquals(paginationMetaDto, Objects.requireNonNull(response.getBody()).getData().getPaginationMeta());
        Assertions.assertEquals(paymentTypeList, Objects.requireNonNull(response.getBody()).getData().getPaginationData());
        Assertions.assertEquals(paymentTypeList.get(0).getType(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getType());
        Assertions.assertEquals(paymentTypeList.get(0).getId(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Success get payment type list pagination", response.getBody().getMeta().getMessage());
    }
}
