package id.fuad.payment.repository.transactional;

import id.fuad.payment.entity.transactional.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    @Query(
            value = "SELECT item_id, item_name, quantity, price FROM inventory",
            countQuery = "SELECT count(item_id) FROM inventory",
            nativeQuery = true
    )
    Page<InventoryEntity> findUsingPageable(Pageable pageable);
}
