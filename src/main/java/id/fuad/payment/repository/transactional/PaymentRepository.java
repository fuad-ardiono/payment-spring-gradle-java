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
            value = "SELECT payment_id, amount, p.payment_type_id as payment_type_id, date, customer_id FROM payment p " +
                    "JOIN payment_type pt on p.payment_type_id = pt.payment_type_id " +
                    "WHERE p.customer_id = ?1 AND pt.type_name LIKE %?2%",
            countQuery = "SELECT count(payment_id) FROM payment p " +
                    "JOIN payment_type pt on p.payment_type_id = pt.payment_type_id " +
                    "WHERE p.customer_id = ?1 AND pt.type_name LIKE %?2%",
            nativeQuery = true
    )
    Page<PaymentEntity> findUsingPageable(Long customerId, String typeName, Pageable pageable);
}
