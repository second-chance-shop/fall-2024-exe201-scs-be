package scs.exe201.secondchanceshopbe.services.iplm;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.enums.MethodPayment;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CartCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.OrderRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.OrderService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceIplm implements OrderService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderResponse> getAllByUserCart() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        List<OrderEntity> orderEntities = orderRepository.findByUserOrderAndStatusCart(userEntity.getUserId());

        if (orderEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return orderEntities.stream()
                .map(EntityToDTO::orderEntityDTO) // Assuming EntityToDTO has a method to convert OrderEntity to OrderResponse
                .toList();
    }
    @Override
    public List<OrderResponse> getAllByUserCheckout() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        List<OrderEntity> orderEntities = orderRepository.findByUserOrderAndStatusHasBuy(userEntity.getUserId());

        if (orderEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return orderEntities.stream()
                .map(EntityToDTO::orderEntityDTO) // Assuming EntityToDTO has a method to convert OrderEntity to OrderResponse
                .toList();
    }


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
                    () -> new NotFoundException("Order not found")
            );

            orderEntity.setQuantity(updateDTO.getQuantity());
        MethodPayment paymentMethod = Arrays.stream(MethodPayment.values())
                .filter(m -> m.name().equals(updateDTO.getNameMethod().toString()))
                .findFirst()
                .orElseThrow(
                        ()-> new NotFoundException("Method payment not found")
                );

        orderEntity.setPaymentMethod(paymentMethod);
        orderRepository.save(orderEntity);
            OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
            return orderResponse;

    }

    @Override
    public OrderResponse createOrder(OrderCreateDTO createDTO) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        ProductEntity productEntity = productRepository.findById(createDTO.getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        MethodPayment paymentMethod = Arrays.stream(MethodPayment.values())
                .filter(m -> m.name().equals(createDTO.getNameMethod().toString()))
                .findFirst()
                .orElseThrow(
                        ()-> new NotFoundException("Method payment not found")
                );

        if(createDTO.getQuantity()<=0){
            throw new ActionFailedException("Quantity is negative or zero");
        }else if (createDTO.getQuantity()>productEntity.getQuantity()){
            throw new ActionFailedException("Quantity is greater than quantity shop have");
        }
        productEntity.setQuantity(productEntity.getQuantity()-createDTO.getQuantity());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserOrder(userEntity);
        orderEntity.setProductOrder(productEntity);
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setStatus(StatusEnum.HAS_BUY);
        orderEntity.setQuantity(createDTO.getQuantity());
        orderEntity.setPaymentMethod(paymentMethod);
        orderRepository.save(orderEntity);
        productRepository.save(productEntity);
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

    @Override
    public OrderResponse createCart(CartCreateDTO createDTO) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        ProductEntity productEntity = productRepository.findById(createDTO.getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserOrder(userEntity);
        orderEntity.setProductOrder(productEntity);
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setStatus(StatusEnum.CART);
        orderEntity.setQuantity(createDTO.getQuantity());
        orderRepository.save(orderEntity);
        OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
        return orderResponse;
    }

    @Override
    public OrderResponse checkoutOrder(long cartId, String methodPayment) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        OrderEntity orderEntity = orderRepository.findByCartIdAndUserId(cartId,userEntity.getUserId());
        if(orderEntity == null) {
            throw new NotFoundException("Order not found");
        }
        if(orderEntity.getStatus().equals(StatusEnum.HAS_BUY)) {
            throw new ActionFailedException("Order is already buy");
        }else if(orderEntity.getStatus().equals(StatusEnum.DELETED)) {
            throw new ActionFailedException("Order is already deleted");
        }
        orderEntity.setStatus(StatusEnum.HAS_BUY);
        if(methodPayment== null){
            throw new ActionFailedException("Method payment is null");
        }
        MethodPayment payment = MethodPayment.valueOf(methodPayment);
        ProductEntity productEntity = productRepository.findById(orderEntity.getProductOrder().getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        if(orderEntity.getQuantity()<=0){
            throw new ActionFailedException("Quantity is negative or zero");
        }else if (orderEntity.getQuantity()>productEntity.getQuantity()){
            throw new ActionFailedException("Quantity is greater than quantity shop have");
        }
        productEntity.setQuantity(productEntity.getQuantity()-orderEntity.getQuantity());
        orderEntity.setPaymentMethod(payment);
        orderEntity.setOrderDate(LocalDate.now());
        orderRepository.save(orderEntity);
        productRepository.save(productEntity);
        return null;
    }
}
