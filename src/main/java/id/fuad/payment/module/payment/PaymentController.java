package id.fuad.payment.module.payment;

import id.fuad.payment.dto.BaseResponseDto;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.exception.UnprocessableContentException;
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
    public ResponseEntity<BaseResponseDto<PaginationDto<List<PaymentDto>>>> getPaymentListPagination(
            @RequestParam(name = "customerId") Long customerId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return response(
                paymentService.getPaymentPagination(customerId, page, pageSize),
                HttpStatus.OK,
                "Success get payment list pagination"
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponseDto<PaymentResponseDto>> getPaymentDetail(
            @PathVariable(name = "id") Long paymentId
    ) throws NotFoundException {
        return response(
                paymentService.getPaymentDetail(paymentId),
                HttpStatus.OK,
                "Success get detail paymentId",
                paymentId.toString()
        );
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<PaymentDto>> createPayment(
            @RequestBody PaymentDto requestData
    ) throws UnprocessableContentException {
        return response(
                paymentService.createPayment(requestData),
                HttpStatus.CREATED,
                "Success create payment"
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponseDto<PaymentDto>> updatePayment(
            @RequestBody PaymentDto requestData,
            @PathVariable(name = "id") Long paymentId
    ) throws UnprocessableContentException {
        return response(
                paymentService.updatePayment(paymentId, requestData),
                HttpStatus.OK,
                "Success update payment"
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponseDto<PaymentDto>> deletePayment(
            @PathVariable(name = "id") Long paymentId
    ) throws UnprocessableContentException {
        return response(
                paymentService.deletePayment(paymentId),
                HttpStatus.OK,
                "Success delete payment"
        );
    }
}
