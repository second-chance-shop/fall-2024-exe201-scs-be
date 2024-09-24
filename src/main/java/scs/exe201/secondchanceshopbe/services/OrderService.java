package scs.exe201.secondchanceshopbe.services;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;

import java.util.List;

@Service
public interface OrderService {

    OrderResponse deleteOrder(long id);

    OrderResponse updateOrder(long id,OrderUpdateDTO updateDTO);

    OrderResponse createOrder(OrderCreateDTO createDTO);

    List<OrderResponse> getAllByUserId(long id);

    List<OrderResponse> getAll();
}
