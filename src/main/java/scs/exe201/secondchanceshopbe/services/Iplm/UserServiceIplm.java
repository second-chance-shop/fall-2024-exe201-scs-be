package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateUserDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;
import scs.exe201.secondchanceshopbe.models.entities.RoleEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.ConflictException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.RoleRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.UserService;
import scs.exe201.secondchanceshopbe.utils.DTOToEntity;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceIplm implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public UserResponse registerNewUser(UserRegisterDTO userRegisterDTO) {

//        userRepository.findByUsername(userRegisterDTO.getUsername())
//                .ifPresent(user -> { throw new ConflictException("Username already exists!"); });
//
//        userRepository.findByEmail(userRegisterDTO.getEmail())
//                .ifPresent(user -> { throw new ConflictException("Email already exists!"); });
//
//        userRepository.findByPhoneNumber(userRegisterDTO.getPhoneNumber())
//                .ifPresent(user -> { throw new ConflictException("Phone number already exists!"); });

            if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
                throw new ConflictException("Username already exists!");
            }
            if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
                throw new ConflictException("Email already exists!");
            }
            if (userRepository.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
                throw new ConflictException("Phone already exists!");
            }
//        RoleEntity role = roleRepository.findRoleName("USER")
//                .orElseThrow(() -> new NotFoundException("Can not get role for create"));
        RoleEntity role = roleRepository.getRoleCustomer();
            if(role == null) {
                throw new NotFoundException("Role not found!");
            }
        String password = passwordEncoder.encode(userRegisterDTO.getPassword());
        try {
            UserEntity userCreate = DTOToEntity.UserResponseToEntity(userRegisterDTO);
            userCreate.setStatus("ACTIVE");
            userCreate.setRoleEntity(role);
            userCreate.setPassword(password);
            userRepository.save(userCreate);
            UserResponse userResponse = EntityToDTO.UserEntityToDTO(userCreate);
            return userResponse;
        } catch (Exception e) {
            throw new ActionFailedException(String.format("Failed register user with reason: %s", e.getMessage()));
        }

    }
    @Override
    public List<UserResponse> getListUser() {
        List<UserEntity> userEntities = userRepository.findAll();
//        var userResponses = userEntities.stream().map
//                (a->EntityToDTO.UserEntityToDTO(a)).toList();
        var userResponses = userEntities.stream().map(EntityToDTO::UserEntityToDTO).toList();
        return userResponses;
    }

    @Override
    public UserResponse userUpdate(UpdateUserDTO updateUserDTO) {
        UserEntity userEntity = userRepository.findById(updateUserDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Cannot find user with ID: %s", updateUserDTO.getId())
                ));
        userEntity.setAvatar(updateUserDTO.getAvatar());
        userEntity.setDob(updateUserDTO.getDob());
        userEntity.setAddress(updateUserDTO.getAddress());
        userEntity.setGender(updateUserDTO.getGender());
        userEntity.setName(updateUserDTO.getName());
        try {
            var item = userRepository.save(userEntity);
            UserResponse userResponse = EntityToDTO.UserEntityToDTO(item);
            return userResponse;
        } catch (Exception e) {
            throw new ActionFailedException(String.format("Failed update user with ID: %s", updateUserDTO.getId()));
        }
    }

    @Override
    public UserResponse userDelete(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Cannot find user with ID: %s", id)
                ));
        userEntity.setStatus("DELETED");
        try {
            var item = userRepository.save(userEntity);
            UserResponse userResponse = EntityToDTO.UserEntityToDTO(item);
            return userResponse;
        } catch (Exception e) {
            throw new ActionFailedException(String.format("Failed delete user with ID: %s", id));
        }
    }

    @Override
    public UserResponse getUsers(String search, Pageable pageable) {
        return null;
    }
}
