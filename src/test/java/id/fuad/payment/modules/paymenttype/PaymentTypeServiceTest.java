package id.fuad.payment.modules.paymenttype;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import id.fuad.payment.entity.transactional.PaymentEntity;
import id.fuad.payment.module.paymenttype.PaymentTypeService;
import id.fuad.payment.module.paymenttype.PaymentTypeServiceImpl;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import id.fuad.payment.repository.masterdata.PaymentTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class PaymentTypeServiceTest {
    @Mock
    PaymentTypeRepository paymentTypeRepository;

    @InjectMocks
    PaymentTypeServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPaymentTypePagination() {
        List<PaymentTypeEntity> paymentTypeEntityList = new ArrayList<>();
        PaymentTypeEntity paymentTypeEntityFirst = new PaymentTypeEntity();
        paymentTypeEntityFirst.setTypeName("cash");
        paymentTypeEntityList.add(paymentTypeEntityFirst);

        Page<PaymentTypeEntity> pageImpl = new PageImpl<>(paymentTypeEntityList);

        Mockito.when(paymentTypeRepository.findUsingPageable(Mockito.any()))
                .thenReturn(pageImpl);

        Integer pageSize = 2;
        Integer page = 1;
        PaginationDto<List<PaymentTypeDto>> process = service.getPaymentTypePagination(page, pageSize);

        Assertions.assertEquals(pageSize, process.getPaginationMeta().getPageSize());
        Assertions.assertEquals(page, process.getPaginationMeta().getPage());
    }
}
