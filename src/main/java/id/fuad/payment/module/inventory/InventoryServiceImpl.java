package id.fuad.payment.module.inventory;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.dto.PaginationMetaDto;
import id.fuad.payment.entity.transactional.InventoryEntity;
import id.fuad.payment.module.inventory.dto.InventoryDto;
import id.fuad.payment.repository.transactional.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public PaginationDto<List<InventoryDto>> getInventoryPagination(Integer page, Integer pageSize) {
        Page<InventoryEntity> inventoryPagination = inventoryRepository
                .findUsingPageable(PageRequest.of(page - 1, pageSize));

        List<InventoryDto> listInventoryDto = inventoryPagination.stream().map(item ->
                InventoryDto
                        .builder()
                        .id(item.getId())
                        .itemName(item.getItemName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build()
        ).toList();

        PaginationMetaDto paginationMeta = PaginationMetaDto.builder()
                .pageSize(pageSize)
                .page(page)
                .totalPage(inventoryPagination.getTotalPages())
                .totalRecord(inventoryPagination.getTotalElements())
                .build();

        return PaginationDto.<List<InventoryDto>>builder()
                .paginationData(listInventoryDto)
                .paginationMeta(paginationMeta)
                .build();
    }
}
