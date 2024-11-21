package scs.exe201.secondchanceshopbe.services.iplm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.OrderRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.services.IPayOsService;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

@Service
@RequiredArgsConstructor
public class PayOsServiceIplm implements IPayOsService {

    private String  clientId = "02aec8ad-36c1-4d40-80da-2530b509127a";
    private String apiKey = "8b026367-1a0b-4cbb-a4dc-751becee2182";
    private String checkSumKey ="d071056aee00537b4afd0f48596aa1fa467c1d4db7b2bb3357fc91223946a5ec";
    private String webhookUrl = "https://scs-api.arisavinh.dev/api/v1/payos";
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    PayOS payOS = new PayOS(clientId, apiKey, checkSumKey);
    @Override
    public  CheckoutResponseData paymentLink(long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order not found")
        );
        int total = (int) Math.round(order.getProductOrder().getPrices() * order.getQuantity());
        ItemData itemData = ItemData.builder()
                .name(order.getProductOrder().getProductName())
                .quantity(order.getQuantity())
                .price(order.getProductOrder().getPrices().intValue())
                .build();
        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderId)
                .amount(total)
                .description("Thanh toán đơn hàng")
                .returnUrl(webhookUrl + "/success")
                .cancelUrl(webhookUrl + "/cancel")
                .item(itemData)
                .build();
        try {
            CheckoutResponseData result = payOS.createPaymentLink(paymentData);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public PaymentLinkData getPaymentLinkData(long orderId) {
        return null;
    }
    @Override
    public OrderResponse actionCancel(long orderCode) {
        OrderEntity order = orderRepository.findById(orderCode).orElseThrow(
                () -> new NotFoundException("Order not found")
        );
        ProductEntity product = productRepository.findById(order.getProductOrder().getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        product.setQuantity(product.getQuantity() +order.getQuantity());
        if(!StatusEnum.CART.equals(order.getStatus())){
            orderRepository.delete(order);
        }
        productRepository.save(product);
        return null;
    }
    @Override
    public OrderResponse actionSuccess(long orderCode) {
        OrderEntity order = orderRepository.findById(orderCode).orElseThrow(
                () -> new NotFoundException("Order not found")
        );
        order.setStatus(StatusEnum.HAS_BUY);
        orderRepository.save(order);
        return null;
    }

}
