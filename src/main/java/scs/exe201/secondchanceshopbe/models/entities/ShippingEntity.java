package scs.exe201.secondchanceshopbe.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

@Data
@Entity
@RequiredArgsConstructor
@Table(name="shipping")
public class ShippingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="shipping_id")
    private long shippingId;
    @Column(name ="name")
    private String name;
    @Column(name ="description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status ;

}
