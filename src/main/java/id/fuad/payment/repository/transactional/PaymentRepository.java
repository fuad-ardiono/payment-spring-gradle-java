package id.fuad.payment.repository.transactional;

import id.fuad.payment.entity.transactional.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findFirstById(Long id);

    @Query(
            value = "SELECT payment_id, amount, payment_type_id, date, customer_id FROM payment WHERE customer_id = ?1",
            countQuery = "SELECT count(payment_id) FROM payment WHERE customer_id = ?1",
            nativeQuery = true
    )
    Page<PaymentEntity> findUsingPageable(Long customerId, Pageable pageable);
}
