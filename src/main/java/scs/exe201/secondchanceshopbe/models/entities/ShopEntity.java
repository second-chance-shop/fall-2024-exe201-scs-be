package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop")
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,name = "shop_id")
    private long shopId;

    @Column(name = "shop_name",nullable = false)
    private String shopName;

    @Column(name = "decsription",nullable = false)
    private String description;

    @Column(name = "shop_email",nullable = false)
    private String shopEmail;

    @Column(name = "shop_phone_number", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String shopPhoneNumber;
//    {
//        "data": null,
//            "code": "SOMETHING_WRONG",
//            "isSuccess": false,
//            "status": "INTERNAL_SERVER_ERROR",
//            "message": "Null value was assigned to a property [class scs.exe201.secondchanceshopbe.models.entities.ShopEntity.valueFollow]
//            of primitive type: 'scs.exe201.secondchanceshopbe.models.entities.ShopEntity.valueFollow' (setter)"
//    }

    @Column(name = "shop_image",nullable = false)
    private String shopImage;

    @Column(name = "back_side_of_cccd",nullable = false)
    private String backSideOfCCCD;

    @Column(name = "front_side_of_cccd",nullable = false)
    private String frontSideOfCCCD;

    @Column(name = "cccd_number",nullable = false)
    private String cccdNumber ;

    @Column(name = "industry",nullable = false)
    private String industry ;

    @Column(name = "date_create",nullable = false)
    private LocalDate dateCreate;

    @Column(name = "shipping_address",nullable = false)
    private String shippingAddress;

    @Column(name = "shop_address",nullable = false)
    private String shopAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status ;

    @Column(name = "value_follow")
    private double valueFollow ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity shopOwner;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private CategoryEntity typeShop;

    @OneToMany(mappedBy = "shopFollow")
    private Set<FollowShopEntity> followers = new HashSet<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductEntity> products = new HashSet<>();

}
