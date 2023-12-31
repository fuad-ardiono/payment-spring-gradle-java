package id.fuad.payment.module.paymenttype;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
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
@RequestMapping(path = "/api/payment-type", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentTypeController extends ControllerBase {
    @Autowired
    PaymentTypeService paymentTypeService;

    @GetMapping
    public ResponseEntity<BaseResponseDto<PaginationDto<List<PaymentTypeDto>>>> getPaymentTypeListPagination(
            @RequestParam(defaultValue = "1", name = "page") Integer page,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer pageSize
    ) {
        return response(
                paymentTypeService.getPaymentTypePagination(page, pageSize),
                HttpStatus.OK,
                "Success get payment type list pagination"
        );
    }
}
