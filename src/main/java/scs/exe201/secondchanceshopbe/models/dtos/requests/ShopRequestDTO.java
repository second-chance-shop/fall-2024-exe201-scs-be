package scs.exe201.secondchanceshopbe.models.dtos.requests;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequestDTO {

    @NotBlank(message = "Shop name is mandatory")
    private String shopName;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @Email(message = "Email should be valid") // Validates email format
    @NotBlank(message = "Email is mandatory")
    private String shopEmail;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{10}$", message = "Phone number should be valid")
    private String shopPhonumber;

    @NotBlank(message = "Shop image is mandatory")
    private String shopImage;

    @NotBlank(message = "Back side of CCCD is mandatory")
    private String backSideOfCCCD;

    @NotBlank(message = "Front side of CCCD is mandatory")
    private String frontSideOfCCCD;

    @NotBlank(message = "CCCD number is mandatory")
    private String cccdNumber;

    @NotBlank(message = "Industry is mandatory")
    private String industry;

    @NotNull(message = "Creation date is mandatory")
    private LocalDate dateCreate;

    @NotBlank(message = "Shipping address is mandatory")
    private String shippingAddress;

    @NotBlank(message = "Shop address is mandatory")
    private String shopAddress;

    @Positive(message = "User ID must be positive")
    private Long userId; // Refers to ShopOwner

    @Positive(message = "Category ID must be positive")
    private Long categoryId; // Refers to TypeShop
}
