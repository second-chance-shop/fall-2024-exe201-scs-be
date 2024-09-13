package scs.exe201.secondchanceshopbe.utils;

import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;

import scs.exe201.secondchanceshopbe.models.entities.UserEntity;

public class DTOToEntity {
    public static UserEntity UserResponseToEntity(UserRegisterDTO userRegister) {
        // Create a new UserEntity object
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegister.getUsername());
        userEntity.setAddress(userRegister.getAddress());
        userEntity.setGender(userRegister.getGender());
        userEntity.setPhoneNumber(userRegister.getPhoneNumber());
        userEntity.setDob(userRegister.getDob());
        userEntity.setEmail(userRegister.getEmail());
        userEntity.setName(userRegister.getName());
        userEntity.setAvatar(userRegister.getAvatar());
        // Return the mapped UserEntity object
        return userEntity;
    }
}
