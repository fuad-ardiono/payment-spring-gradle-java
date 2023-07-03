package id.fuad.payment.entity.transactional;

import id.fuad.payment.entity.masterdata.PaymentTypeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "payment")
public class PaymentEntity {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "amount")
    BigInteger amount;

    @OneToOne
    @JoinColumn(name="payment_type_id", referencedColumnName = "payment_type_id")
    PaymentTypeEntity paymentType;

    @Column(name = "customer_id")
    Long customerId;
}
