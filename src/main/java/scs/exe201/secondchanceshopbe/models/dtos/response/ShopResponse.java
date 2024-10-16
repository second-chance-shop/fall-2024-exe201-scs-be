package scs.exe201.secondchanceshopbe.models.dtos.response;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponse {
    private long shopId;
    private String shopName;
    private String description;
    private String shopEmail;
    private String shopPhonumber;
    private String shopImage;
    private String backSideOfCCCD;
    private String frontSideOfCCCD;
    private String cccdNumber;
    private String industry;
    private LocalDate dateCreate;
    private String shippingAddress;
    private String shopAddress;
    private String ownerName; // Optionally added if needed to represent the Shop Owner
    private String categoryName; // Optionally added if needed to represent the Category type
    private long userId;
}

