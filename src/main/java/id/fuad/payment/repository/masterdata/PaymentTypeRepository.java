package id.fuad.payment.repository.masterdata;

import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Long> {
    @Query(
            value = "SELECT payment_type_id, type_name FROM payment_type",
            countQuery = "SELECT count(payment_type_id) FROM payment_type",
            nativeQuery = true
    )
    Page<PaymentTypeEntity> findUsingPageable(Pageable pageable);

    PaymentTypeEntity findFirstById(Long id);
}
