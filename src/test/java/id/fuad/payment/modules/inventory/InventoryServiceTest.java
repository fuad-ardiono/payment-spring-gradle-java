package id.fuad.payment.modules.inventory;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import id.fuad.payment.entity.transactional.InventoryEntity;
import id.fuad.payment.module.inventory.InventoryServiceImpl;
import id.fuad.payment.module.inventory.dto.InventoryDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import id.fuad.payment.repository.transactional.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class InventoryServiceTest {
    @Mock
    InventoryRepository inventoryRepository;

    @InjectMocks
    InventoryServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInventoryPagination() {
        List<InventoryEntity> inventoryList = new ArrayList<>();
        InventoryEntity inventoryEntityFirst = new InventoryEntity();
        inventoryEntityFirst.setItemName("Shoes");
        inventoryEntityFirst.setId(1L);
        inventoryEntityFirst.setPrice(new BigInteger("8000"));
        inventoryEntityFirst.setQuantity(100);

        inventoryList.add(inventoryEntityFirst);

        Page<InventoryEntity> pageImpl = new PageImpl<>(inventoryList);

        Mockito.when(inventoryRepository.findUsingPageable(Mockito.any()))
                .thenReturn(pageImpl);

        Integer pageSize = 2;
        Integer page = 1;
        PaginationDto<List<InventoryDto>> process = service.getInventoryPagination(page, pageSize);

        Assertions.assertEquals(pageSize, process.getPaginationMeta().getPageSize());
        Assertions.assertEquals(page, process.getPaginationMeta().getPage());
    }
}
