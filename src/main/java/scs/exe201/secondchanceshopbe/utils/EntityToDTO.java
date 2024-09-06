package scs.exe201.secondchanceshopbe.utils;

import org.springframework.stereotype.Component;
import scs.exe201.secondchanceshopbe.models.dtos.respones.UserResponse;
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
}
