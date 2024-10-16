package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
    private long productId;
    private int quantity;
    private long userId;
    private String nameMethod;
}

