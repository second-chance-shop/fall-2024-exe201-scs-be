package scs.exe201.secondchanceshopbe.models.entities;

import jakarta.persistence.*;
import lombok.*;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop_follow",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "shop_id"})})
public class FollowShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", unique = true, nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "date-follow")
    private LocalDate dateFollow;
    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userFollow;

    // Many-to-One relationship with Shop
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopEntity shopFollow;

}
