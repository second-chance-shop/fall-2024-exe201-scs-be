package scs.exe201.secondchanceshopbe.utils;

import scs.exe201.secondchanceshopbe.models.dtos.response.*;
import scs.exe201.secondchanceshopbe.models.entities.*;


public class EntityToDTO {

    public static UserResponse UserEntityToDTO(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();

        // Map fields from UserEntity to UserResponse
        userResponse.setUserId(userEntity.getUserId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setAddress(userEntity.getAddress());
        userResponse.setGender(userEntity.getGender());
        userResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userResponse.setDob(userEntity.getDob());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setName(userEntity.getName());
        userResponse.setAvatar(userEntity.getAvatar());
        userResponse.setStatus(userEntity.getStatus());
        return userResponse;
    }
    public static CommentResponse commentToEntityDTO(CommentEntity commentEntity)  {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(commentEntity.getCommentId());
        commentResponse.setContent(commentEntity.getContent());
        commentResponse.setUserId(commentEntity.getUserComment().getUserId());
        commentResponse.setCreatedAt(commentEntity.getDateCreate());
        commentResponse.setProductId(commentEntity.getProduct().getProductId());
        return commentResponse;
    }
    public static RatingResponse ratingoEntityDTOT(RatingEntity ratingEntity)  {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setCommentId(ratingEntity.getRatingId());
        ratingResponse.setStart(ratingEntity.getStar());
        ratingResponse.setUserId(ratingEntity.getUserRating().getUserId());
        ratingResponse.setCreatedAt(ratingEntity.getDateCreate());
        ratingResponse.setProductId(ratingEntity.getProduct().getProductId());
        return  ratingResponse;
    }
    public static NotificationResponse notificationEntityDTO(NotificationEntity notificationEntity)  {
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setNotificationId(notificationEntity.getNotificationId());
        notificationResponse.setContent(notificationEntity.getContent());
        notificationResponse.setTitle(notificationEntity.getTitle());
        notificationResponse.setUserId(notificationEntity.getCreateNotification().getUserId());
        notificationResponse.setDateCreate(notificationEntity.getDateCreate());
        return notificationResponse;
    }
    public  static OrderResponse orderEntityDTO(OrderEntity orderEntity)  {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderEntity.getOrderId());
        orderResponse.setOrderDate(orderEntity.getOrderDate());
        orderResponse.setStatus(orderEntity.getStatus());
        orderResponse.setPaymentId(orderEntity.getPaymentOrder().getPaymentId());
        orderResponse.setQuantity(orderEntity.getQuantity());
        orderResponse.setProductId(orderEntity.getProductOrder().getProductId());
        orderResponse.setUserId(orderEntity.getUserOrder().getUserId());
        return orderResponse;
    }
}
