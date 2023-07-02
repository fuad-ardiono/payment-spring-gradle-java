package id.fuad.payment.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "payment_type")
@Data
public class PaymentTypeEntity {
    @Id
    @Column(name = "payment_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type_name")
    String typeName;
}
