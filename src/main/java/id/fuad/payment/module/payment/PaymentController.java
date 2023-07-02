package id.fuad.payment.module.payment;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.PaymentResponseDto;
import id.fuad.payment.utils.base.ControllerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController extends ControllerBase {
    @Autowired
    PaymentService paymentService;

    @GetMapping
    ResponseEntity<BaseResponseDto<PaginationDto<List<PaymentDto>>>> getPaymentListPagination(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return response(paymentService.getPaymentPagination(page, pageSize), HttpStatus.OK, "Success get payment list pagination");
    }

    @GetMapping("{id}")
    ResponseEntity<BaseResponseDto<PaymentResponseDto>> getPaymentDetail(
        @PathVariable(name = "id") Long paymentId
    ) throws NotFoundException {
        return response(paymentService.getPaymentDetail(paymentId), HttpStatus.OK, "Success get detail paymentId", paymentId.toString());
    }
}