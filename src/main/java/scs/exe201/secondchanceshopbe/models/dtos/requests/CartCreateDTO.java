package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartCreateDTO {
    private long productId;
    private int quantity;
}
