package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateUserDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;
import scs.exe201.secondchanceshopbe.models.entities.RoleEntity;
import scs.exe201.secondchanceshopbe.models.entities.StatusEnum;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.ConflictException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.RoleRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.OTPService;
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
    private final OTPService otpService;
    private PasswordEncoder passwordEncoder;


    @Override
    public UserResponse registerNewUser(UserRegisterDTO userRegisterDTO) {

       Optional<UserEntity> userEntity = userRepository.findByEmail(userRegisterDTO.getEmail());
       if (userEntity.isPresent()&& userEntity.get().getStatus().equals("VERIFY")) {

           otpService.generateOTPCodeAgain(userRegisterDTO.getEmail());
           throw new ActionFailedException("Email này đã được đăng kí nhưng với username"+ userEntity.get().getUsername()+ " chưa xác thực đã gửi otp để xác thưc vui lòng check email");
       } else if (userEntity.isPresent()&& userEntity.get().getStatus().equals("ACTIVE")) {
           throw new ConflictException("Email already exists!");
       }

        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new ConflictException("Username already exists!");
        }
            if (userRepository.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
                throw new ConflictException("Phone already exists!");
            }
        RoleEntity role = roleRepository.getRoleCustomer();
            if(role == null) {
                throw new NotFoundException("Role not found!");
            }
        String password = passwordEncoder.encode(userRegisterDTO.getPassword());
            UserEntity userCreate = DTOToEntity.UserResponseToEntity(userRegisterDTO);
            userCreate.setStatus(StatusEnum.VERIFY);
            userCreate.setRoleEntity(role);
            userCreate.setPassword(password);
            userRepository.save(userCreate);
            UserResponse userResponse = EntityToDTO.UserEntityToDTO(userCreate);
            return userResponse;

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
    public UserResponse userDelete(long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Cannot find user with ID: %s", id)
                ));
        userEntity.setStatus(StatusEnum.DELETED);
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

    @Override
    public void ActiveUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
        userEntity.setStatus(StatusEnum.ACTIVE);
        userRepository.save(userEntity);
    }


    @Override
    public UserResponse getUserById(long id) {
        UserEntity uEntity = userRepository.findById(id).orElseThrow(
            ()-> new NotFoundException("user not found")
        );
        UserResponse userResponse = EntityToDTO.UserEntityToDTO(uEntity);
        return userResponse;
    }
}
