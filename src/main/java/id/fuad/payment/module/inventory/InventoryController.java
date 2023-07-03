package id.fuad.payment.module.inventory;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.inventory.dto.InventoryDto;
import id.fuad.payment.utils.base.ControllerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController extends ControllerBase {
    @Autowired
    InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<BaseResponseDto<PaginationDto<List<InventoryDto>>>> getInventoryListPagination(
            @RequestParam(defaultValue = "1", name="page") Integer page,
            @RequestParam(defaultValue = "10", name="pageSize") Integer pageSize
    ) {
        return response(
                inventoryService.getInventoryPagination(page, pageSize),
                HttpStatus.OK,
                "Sucess get inventory list pagination"
        );
    }
}
