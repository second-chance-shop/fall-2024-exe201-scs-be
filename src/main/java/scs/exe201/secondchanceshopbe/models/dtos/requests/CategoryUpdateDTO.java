package scs.exe201.secondchanceshopbe.models.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDTO {
    @NotBlank(message = "Category name cannot be empty")
    private String categoryName;
    @NotBlank(message = "Description name cannot be empty")
    private String description;
}
