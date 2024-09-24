package scs.exe201.secondchanceshopbe.models.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long orderId;
    private long userId;
    private long productId;
    private int quantity;
    private LocalDate orderDate;
    private String status;
    private long paymentId;
}
