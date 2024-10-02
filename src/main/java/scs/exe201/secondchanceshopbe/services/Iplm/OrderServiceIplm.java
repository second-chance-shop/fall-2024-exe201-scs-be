package scs.exe201.secondchanceshopbe.services.Iplm;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;
import scs.exe201.secondchanceshopbe.models.entities.PaymentEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.OrderRepository;
import scs.exe201.secondchanceshopbe.repositories.PaymentRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.OrderService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceIplm implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository orderPaymentRepository;

    public OrderServiceIplm(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, PaymentRepository orderPaymentRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderPaymentRepository = orderPaymentRepository;
    }

    @Override
    public OrderResponse deleteOrder(long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order not found")
        );
        orderEntity.setStatus("DELETED");
        orderRepository.save(orderEntity);
        OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrder(long id, OrderUpdateDTO updateDTO) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order not found")
        );

        PaymentEntity paymentEntity = orderPaymentRepository.findById(updateDTO.getPaymentId()).orElseThrow(
                () -> new NotFoundException("Payment not found")
        );
        orderEntity.setQuantity(updateDTO.getQuantity());
        orderEntity.setPaymentOrder(paymentEntity);
        orderRepository.save(orderEntity);
        OrderResponse orderResponse = EntityToDTO.orderEntityDTO(orderEntity);
        return orderResponse;
    }

    @Override
    public OrderResponse createOrder(OrderCreateDTO createDTO) {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity userEntity = userRepository.findById(createDTO.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        ProductEntity productEntity = productRepository.findById(createDTO.getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        PaymentEntity paymentEntity = orderPaymentRepository.findById(createDTO.getPaymentId()).orElseThrow(
                () -> new NotFoundException("Payment not found")
        );
        orderEntity.setUserOrder(userEntity);
        orderEntity.setProductOrder(productEntity);
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setStatus("ACTIVE");
        orderEntity.setQuantity(createDTO.getQuantity());
        orderEntity.setPaymentOrder(paymentEntity);
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
