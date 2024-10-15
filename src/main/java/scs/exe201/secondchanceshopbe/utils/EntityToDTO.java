package scs.exe201.secondchanceshopbe.utils;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import scs.exe201.secondchanceshopbe.models.dtos.response.*;
import scs.exe201.secondchanceshopbe.models.entities.*;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;

@RequiredArgsConstructor
public class EntityToDTO {

    public static UserResponse UserEntityToDTO(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .address(userEntity.getAddress())
                .gender(userEntity.getGender())
                .phoneNumber(userEntity.getPhoneNumber())
                .dob(userEntity.getDob())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .avatar(userEntity.getAvatar())
                .status(userEntity.getStatus().toString())
                .build();
    }

    public static CommentResponse commentToEntityDTO(CommentEntity commentEntity) {
        return CommentResponse.builder()
                .commentId(commentEntity.getCommentId())
                .content(commentEntity.getContent())
                .userId(commentEntity.getUserComment().getUserId())
                .createdAt(commentEntity.getDateCreate())
                .productId(commentEntity.getProduct().getProductId())
                .build();
    }

    public static RatingResponse ratingoEntityDTOT(RatingEntity ratingEntity) {
        return RatingResponse.builder()
                .commentId(ratingEntity.getRatingId())
                .start(ratingEntity.getStar())
                .userId(ratingEntity.getUserRating().getUserId())
                .createdAt(ratingEntity.getDateCreate())
                .productId(ratingEntity.getProduct().getProductId())
                .build();
    }

    public static NotificationResponse notificationEntityDTO(NotificationEntity notificationEntity) {
        return NotificationResponse.builder()
                .notificationId(notificationEntity.getNotificationId())
                .content(notificationEntity.getContent())
                .title(notificationEntity.getTitle())
                .userId(notificationEntity.getCreateNotification().getUserId())
                .dateCreate(notificationEntity.getDateCreate())
                .build();
    }

    public static OrderResponse orderEntityDTO(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .orderDate(orderEntity.getOrderDate())
                .status(orderEntity.getStatus())
                .paymentId(orderEntity.getPaymentOrder().getPaymentId())
                .quantity(orderEntity.getQuantity())
                .productId(orderEntity.getProductOrder().getProductId())
                .userId(orderEntity.getUserOrder().getUserId())
                .build();
    }

    public static ProductResponse productEntityToDTO(ProductEntity productEntity) {
        return ProductResponse.builder()
                .createByUsername(productEntity.getCreateBy().getUsername())
                .categoryNames(productEntity.getCategories().stream()
                        .map(category -> category.getCategoryName()) // Assuming CategoryEntity has getCategoryName()
                        .collect(Collectors.toSet()))
                .dateCreate(productEntity.getDateCreate())
                .prices(productEntity.getPrices())
                .quantity(productEntity.getQuantity())
                .status(productEntity.getStatus())
                .description(productEntity.getDescription())
                .image(productEntity.getImage())
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .build();
    }

    public static CategoryResponse categoryEntityToDTO(CategoryEntity categoryEntity) {
        return CategoryResponse.builder()
                .categoryId(categoryEntity.getCategoryId())
                .categoryName(categoryEntity.getCategoryName())
                .build();
    }

    public static ShopResponse shopEntityTODTO(ShopEntity shopEntity) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new SecurityException("You are not logged in");
        }
        return ShopResponse.builder()
                .shopId(shopEntity.getShopId())
                .shopName(shopEntity.getShopName())
                .description(shopEntity.getDescription())
                .shopEmail(shopEntity.getShopEmail())
                .shopPhonumber(shopEntity.getShopPhonumber())
                .shopImage(shopEntity.getShopImage())
                .backSideOfCCCD(shopEntity.getBackSideOfCCCD())
                .frontSideOfCCCD(shopEntity.getFrontSideOfCCCD())
                .cccdNumber(shopEntity.getCccdNumber())
                .industry(shopEntity.getIndustry())
                .dateCreate(shopEntity.getDateCreate())
                .shippingAddress(shopEntity.getShippingAddress())
                .shopAddress(shopEntity.getShopAddress())
                .ownerName(auth.getName())
                .categoryName(shopEntity.getTypeShop().getCategoryName())
                .build();
    }
}
