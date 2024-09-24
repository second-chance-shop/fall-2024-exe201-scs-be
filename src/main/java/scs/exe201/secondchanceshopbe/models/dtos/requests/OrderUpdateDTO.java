package scs.exe201.secondchanceshopbe.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderUpdateDTO {

    private int quantity;
    private long paymentId;
}
