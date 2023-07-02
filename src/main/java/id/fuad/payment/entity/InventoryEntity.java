package id.fuad.payment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "inventory")
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
