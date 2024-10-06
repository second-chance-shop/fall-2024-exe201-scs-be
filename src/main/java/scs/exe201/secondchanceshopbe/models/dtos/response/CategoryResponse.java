package scs.exe201.secondchanceshopbe.models.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
}
