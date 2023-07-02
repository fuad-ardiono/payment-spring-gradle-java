package id.fuad.payment.entity.masterdata;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "payment_type")
public class PaymentTypeEntity {
    @Id
    @Column(name = "payment_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type_name")
    String typeName;
}
