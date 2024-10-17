package scs.exe201.secondchanceshopbe.services.iplm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.enums.MethodPayment;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.OrderRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.OrderService;
import scs.exe201.secondchanceshopbe.utils.Constants;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceIplm implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;




    @Override
    public OrderResponse deleteOrder(long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order not found")
        );
        orderEntity.setStatus(StatusEnum.DELETED);
        orderRepository.save(orderEntity);
        OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrder(long id, OrderUpdateDTO updateDTO) {

            OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(Constants.ORDER_NOT_FOUND)
            );

            orderEntity.setQuantity(updateDTO.getQuantity());
        MethodPayment paymentMethod = Arrays.stream(MethodPayment.values())
                .filter(m -> m.name().equals(updateDTO.getNameMethod().toString()))
                .findFirst()
                .orElseThrow(
                        ()-> new NotFoundException(Constants.METHOD_PAYMENT_NOT_FOUND)
                );

        orderEntity.setPaymentMethod(paymentMethod);
        orderRepository.save(orderEntity);
            OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
            return orderResponse;

    }

    @Override
    public OrderResponse createOrder(OrderCreateDTO createDTO) {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity userEntity = userRepository.findById(createDTO.getUserId()).orElseThrow(
                () -> new NotFoundException(Constants.USER_NOT_FOUND)
        );
        ProductEntity productEntity = productRepository.findById(createDTO.getProductId()).orElseThrow(
                () -> new NotFoundException(Constants.PRODUCT_NOT_FOUND)
        );
        MethodPayment paymentMethod = Arrays.stream(MethodPayment.values())
                .filter(m -> m.name().equals(createDTO.getNameMethod().toString()))
                .findFirst()
                .orElseThrow(
                        ()-> new NotFoundException(Constants.METHOD_PAYMENT_NOT_FOUND)
                );
        orderEntity.setUserOrder(userEntity);
        orderEntity.setProductOrder(productEntity);
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setStatus(StatusEnum.ACTIVE);
        orderEntity.setQuantity(createDTO.getQuantity());
        orderEntity.setPaymentMethod(paymentMethod);
        orderRepository.save(orderEntity);
        OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllByUserId(long id) {
        List<OrderEntity> orderEntities = orderRepository.findByUserOrder(id);
        var orderResponse = orderEntities.stream().map(EntityToDTO::orderEntityDTO).toList();
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAll() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        var orderResponse = orderEntities.stream().map(EntityToDTO::orderEntityDTO).toList();
        return orderResponse;
    }
}
