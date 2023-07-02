package id.fuad.payment.repository;

import id.fuad.payment.entity.PaymentTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Long> {
    @Query(value = "SELECT payment_type_id, type_name FROM payment_type", nativeQuery = true)
    Page<PaymentTypeEntity> findUsingPageable(Pageable pageable);
}
