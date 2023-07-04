package id.fuad.payment.modules.inventory;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.dto.PaginationMetaDto;
import id.fuad.payment.module.inventory.InventoryController;
import id.fuad.payment.module.inventory.InventoryService;
import id.fuad.payment.module.inventory.dto.InventoryDto;
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

public class InventoryControllerTest {
    @Mock
    InventoryService inventoryService;

    @InjectMocks
    InventoryController inventoryController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInventoryListPagination() {
        List<InventoryDto> inventoryList = new ArrayList<>();
        inventoryList.add(InventoryDto.builder().price(new BigInteger("2000")).quantity(2).itemName("Shoes").id(1L).build());
        inventoryList.add(InventoryDto.builder().price(new BigInteger("8000")).quantity(2).itemName("T-Shirt").id(1L).build());

        PaginationMetaDto paginationMetaDto = PaginationMetaDto.builder()
                .page(2)
                .pageSize(10)
                .totalPage(2)
                .totalRecord(20L)
                .build();

        PaginationDto<List<InventoryDto>> pagination = PaginationDto.<List<InventoryDto>>builder()
                .paginationData(inventoryList)
                .paginationMeta(paginationMetaDto)
                .build();

        Mockito.when(inventoryService.getInventoryPagination(Mockito.eq(2), Mockito.eq(10)))
                .thenReturn(pagination);

        ResponseEntity<BaseResponseDto<PaginationDto<List<InventoryDto>>>> response = inventoryController
                .getInventoryListPagination(2, 10);

        Assertions.assertEquals(paginationMetaDto, Objects.requireNonNull(response.getBody()).getData().getPaginationMeta());
        Assertions.assertEquals(inventoryList, Objects.requireNonNull(response.getBody()).getData().getPaginationData());
        Assertions.assertEquals(inventoryList.get(0).getId(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getId());
        Assertions.assertEquals(inventoryList.get(0).getItemName(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getItemName());
        Assertions.assertEquals(inventoryList.get(0).getQuantity(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getQuantity());
        Assertions.assertEquals(inventoryList.get(0).getPrice(), Objects.requireNonNull(response.getBody()).getData().getPaginationData().get(0).getPrice());
        Assertions.assertEquals(paginationMetaDto.getTotalPage(), Objects.requireNonNull(response.getBody()).getData().getPaginationMeta().getTotalPage());
        Assertions.assertEquals(paginationMetaDto.getTotalRecord(), Objects.requireNonNull(response.getBody()).getData().getPaginationMeta().getTotalRecord());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Sucess get inventory list pagination", response.getBody().getMeta().getMessage());
    }
}
