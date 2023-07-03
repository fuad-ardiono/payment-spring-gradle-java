package id.fuad.payment.entity.transactional;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "inventory")
public class InventoryEntity {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "item_name")
    String itemName;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "price")
    BigInteger price;
}
