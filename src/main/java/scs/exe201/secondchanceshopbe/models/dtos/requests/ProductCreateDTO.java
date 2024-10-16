package scs.exe201.secondchanceshopbe.models.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    @NotNull(message = "Quantity is mandatory")
    private Long quantity;

    private String description;

    @NotNull(message = "Categories are mandatory")
    private Set<Long> categoryIds;

    @NotNull(message = "Price is mandatory")
    private Long prices;

    private String image;



}
