package id.fuad.payment.module.payment;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.module.payment.dto.PaymentDto;
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
@RequestMapping(path = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController extends ControllerBase {
    @Autowired
    PaymentService paymentService;

    @GetMapping
    ResponseEntity<BaseResponseDto<PaginationDto<List<PaymentDto>>>> getPayment(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return response(paymentService.getPayment(page, pageSize), HttpStatus.OK);
    }
}
