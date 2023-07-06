package id.fuad.payment.module.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.fuad.payment.dto.PaginationDto;
import id.fuad.payment.dto.PaginationMetaDto;
import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import id.fuad.payment.entity.transactional.PaymentEntity;
import id.fuad.payment.exception.NotFoundException;
import id.fuad.payment.exception.UnprocessableContentException;
import id.fuad.payment.module.payment.dto.PaymentDto;
import id.fuad.payment.module.payment.dto.PaymentResponseDto;
import id.fuad.payment.module.paymenttype.dto.PaymentTypeDto;
import id.fuad.payment.repository.masterdata.PaymentTypeRepository;
import id.fuad.payment.repository.transactional.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Autowired
    PaymentValidator paymentValidator;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public PaginationDto<List<PaymentDto>> getPaymentPagination(Long customerId, String typeName, Integer page, Integer pageSize) {
        String key = "customerId="+customerId+"&typeName="+typeName+"&page"+page+"&pageSize="+pageSize;
        String keyBase64 = "payment:list:"+Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
        ObjectMapper objectMapper = new ObjectMapper();

        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(keyBase64))) {
            try {
                String cacheValue = stringRedisTemplate.opsForValue().get(keyBase64);

                return objectMapper.readValue(cacheValue, new TypeReference<>() {});
            } catch (JsonProcessingException ignored) {
                stringRedisTemplate.delete(keyBase64);

                return getPaymentPagination(customerId, typeName, page, pageSize);
            }
        } else {
            Page<PaymentEntity> paymentPagination = paymentRepository.findUsingPageable(customerId, typeName, PageRequest.of(page - 1, pageSize));

            List<PaymentDto> listPaymentDto = paymentPagination.stream().map(item ->
                    PaymentDto
                            .builder()
                            .paymentId(item.getId())
                            .amount(item.getAmount())
                            .customerId(item.getCustomerId())
                            .paymentTypeId(item.getPaymentType().getId())
                            .paymentType(
                                    PaymentTypeDto.builder()
                                            .id(item.getPaymentType().getId())
                                            .type(item.getPaymentType().getTypeName())
                                            .build()
                            )
                            .build()
            ).toList();

            PaginationMetaDto paginationMeta = PaginationMetaDto.builder()
                    .pageSize(pageSize)
                    .page(page)
                    .totalPage(paymentPagination.getTotalPages())
                    .totalRecord(paymentPagination.getTotalElements())
                    .build();

            PaginationDto<List<PaymentDto>> resp = PaginationDto.<List<PaymentDto>>builder()
                    .paginationData(listPaymentDto)
                    .paginationMeta(paginationMeta)
                    .build();


            try {
                String json = objectMapper.writeValueAsString(resp);
                stringRedisTemplate.opsForValue().set(keyBase64, json, Duration.ofMinutes(5));
            } catch (JsonProcessingException ignored) {}

            return resp;
        }
    }

    @Override
    public PaymentResponseDto getPaymentDetail(Long paymentId) throws NotFoundException {
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        PaymentTypeDto paymentTypeDto = PaymentTypeDto.builder()
                .type(paymentEntity.getPaymentType().getTypeName())
                .id(paymentEntity.getPaymentType().getId())
                .build();

        return PaymentResponseDto.builder()
                .id(paymentEntity.getId())
                .paymentType(paymentTypeDto)
                .customerId(paymentEntity.getCustomerId())
                .amount(paymentEntity.getAmount())
                .build();
    }

    @Override
    public PaymentDto createPayment(PaymentDto requestData) throws UnprocessableContentException {
        paymentValidator.validatePayment(requestData);

        Set<String> keys = stringRedisTemplate.keys("payment:list:*");
        stringRedisTemplate.delete(Objects.requireNonNull(keys));

        PaymentTypeEntity paymentType = paymentTypeRepository.findFirstById(requestData.getPaymentTypeId());

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(requestData.getAmount());
        paymentEntity.setCustomerId(requestData.getCustomerId());
        paymentEntity.setPaymentType(paymentType);

        PaymentEntity recordPayment = paymentRepository.saveAndFlush(paymentEntity);

        return PaymentDto.builder()
                .paymentId(recordPayment.getId())
                .amount(recordPayment.getAmount())
                .paymentTypeId(recordPayment.getPaymentType().getId())
                .customerId(recordPayment.getCustomerId())
                .build();
    }

    @Override
    public PaymentDto updatePayment(Long paymentId, PaymentDto requestData) throws UnprocessableContentException {
        paymentValidator.validatePayment(paymentId, requestData);

        Set<String> keys = stringRedisTemplate.keys("payment:list:*");
        stringRedisTemplate.delete(Objects.requireNonNull(keys));

        PaymentEntity payment = paymentRepository.findFirstById(paymentId);
        PaymentTypeEntity paymentType = paymentTypeRepository.findFirstById(requestData.getPaymentTypeId());

        payment.setAmount(requestData.getAmount());
        payment.setPaymentType(paymentType);
        payment.setCustomerId(requestData.getCustomerId());

        PaymentEntity updatedPayment = paymentRepository.saveAndFlush(payment);

        return PaymentDto.builder()
                .paymentId(updatedPayment.getId())
                .amount(updatedPayment.getAmount())
                .paymentTypeId(updatedPayment.getPaymentType().getId())
                .customerId(updatedPayment.getCustomerId())
                .build();
    }

    @Override
    public PaymentDto deletePayment(Long paymentId) throws UnprocessableContentException {
        paymentValidator.validatePayment(paymentId);

        Set<String> keys = stringRedisTemplate.keys("payment:list:*");
        stringRedisTemplate.delete(Objects.requireNonNull(keys));

        PaymentEntity payment = paymentRepository.findFirstById(paymentId);
        paymentRepository.delete(payment);

        return PaymentDto.builder()
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .paymentTypeId(payment.getPaymentType().getId())
                .customerId(payment.getCustomerId())
                .build();
    }
}
