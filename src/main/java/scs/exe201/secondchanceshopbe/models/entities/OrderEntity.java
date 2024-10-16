package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scs.exe201.secondchanceshopbe.models.dtos.enums.MethodPayment;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private long orderId;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity userOrder;

    @OneToOne
    @JoinColumn(name= "product_id",nullable = false)
    @JsonIgnore
    private ProductEntity productOrder;

    @Column(name ="quantity")
    private int quantity;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_payment")
    private MethodPayment paymentMethod;

}
