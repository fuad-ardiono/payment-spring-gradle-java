package id.fuad.payment.module.inventory;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.inventory.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    PaginationDto<List<InventoryDto>> getInventory(Integer page, Integer pageSize);
}
