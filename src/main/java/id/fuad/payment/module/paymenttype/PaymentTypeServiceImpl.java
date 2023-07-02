package id.fuad.payment.module.paymenttype;

import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.dto.PaginationMetaDto;
import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import id.fuad.payment.repository.masterdata.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentMethodRepository;

    @Override
    public PaginationDto<List<PaymentTypeDto>> getPaymentTypePagination(Integer page, Integer pageSize) {
        Page<PaymentTypeEntity> paymentMethodPage = paymentMethodRepository
                .findUsingPageable(PageRequest.of(page - 1, pageSize));

        List<PaymentTypeDto> listPaymentMethodDto = paymentMethodPage.stream().map(item ->
                PaymentTypeDto
                        .builder()
                        .id(item.getId())
                        .type(item.getTypeName())
                        .build()
        ).toList();

        PaginationMetaDto paginationMeta = PaginationMetaDto.builder()
                .pageSize(pageSize)
                .page(page)
                .totalPage(paymentMethodPage.getTotalPages())
                .totalRecord(paymentMethodPage.getTotalElements())
                .build();

        return PaginationDto.<List<PaymentTypeDto>>builder()
                .paginationData(listPaymentMethodDto)
                .paginationMeta(paginationMeta)
                .build();
    }


}
