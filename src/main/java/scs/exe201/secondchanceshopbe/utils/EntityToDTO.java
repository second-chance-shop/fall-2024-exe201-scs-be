package scs.exe201.secondchanceshopbe.utils;

import scs.exe201.secondchanceshopbe.models.dtos.response.CommentResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;


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
    public static CommentResponse commentDTOToEntity(CommentEntity commentEntity)  {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(commentEntity.getCommentId());
        commentResponse.setContent(commentEntity.getContent());
        commentResponse.setUserId(commentEntity.getUserComment().getUserId());
        commentResponse.setCreatedAt(commentEntity.getDateCreate());
        commentResponse.setProductId(commentEntity.getProduct().getProductId());
        return new CommentResponse();
    }
}
