package scs.exe201.secondchanceshopbe.models.dtos.response;


import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long productId;
    private String productName;
    private Long quantity;
    private String description;
    private Set<String> categoryNames; // Assuming you'd return category names in response
    private Long prices;
    private String status;
    private String image;
    private LocalDate dateCreate;
    private String createByUsername; // Assuming you'd return the username instead of user entity

}
