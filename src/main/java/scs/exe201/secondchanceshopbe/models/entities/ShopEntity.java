package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private int shopId;

    @Column(name = "shop_name",nullable = false)
    private String shopName;

    @Column(name = "decsription",nullable = false)
    private String description;

    @Column(name = "shop_email",nullable = false)
    private String shopEmail;

    @Column(name = "shop_phonumber",nullable = false)
    private String shopPhonumber;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity shopOwner;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private CategoryEntity typeShop;

}
