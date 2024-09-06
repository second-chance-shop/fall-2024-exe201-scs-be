package scs.exe201.secondchanceshopbe.utils;

import scs.exe201.secondchanceshopbe.models.dtos.respones.UserResponse;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;

public class DTOToEntity {
    public static UserEntity UserResponseToEntity(UserResponse userResponse) {
        // Create a new UserEntity object
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(userResponse.getUserId());
        userEntity.setUsername(userResponse.getUsername());
        userEntity.setAddress(userResponse.getAddress());
        userEntity.setGender(userResponse.getGender());
        userEntity.setPhoneNumber(userResponse.getPhoneNumber());
        userEntity.setDob(userResponse.getDob());
        userEntity.setEmail(userResponse.getEmail());
        userEntity.setName(userResponse.getName());
        userEntity.setAvatar(userResponse.getAvatar());
        userEntity.setStatus(userResponse.getStatus());

        // Return the mapped UserEntity object
        return userEntity;
    }
}
