package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.entities.RoleEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.repositories.RoleRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.UserService;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class UserServiceIplm implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> registerNewUser(UserRegisterDTO userRegisterDTO) {
        try {
            // Kiểm tra trùng lặp cho username
            Optional<UserEntity> checkUsername = userRepository.findByUsername(userRegisterDTO.getUsername());
            if (checkUsername.isPresent()) {
               // throw new PracticeException(HttpStatus.CONFLICT, "Username already exists!");
            }

            // Kiểm tra trùng lặp cho email
//            List<UserEntity> checkEmail = userRepository.findByEmail(userRegisterDTO.getEmail());
//            if (!checkEmail.isEmpty()) {
//             //   throw new PracticeException(HttpStatus.CONFLICT, "Email already exists!");
//            }
//
//            // Kiểm tra trùng lặp cho phoneNumber
//            List<UserEntity> checkPhone = userRepository.findByPhoneNumber(userRegisterDTO.getPhoneNumber());
//            if (!checkPhone.isEmpty()) {
//              //  throw new PracticeException(HttpStatus.CONFLICT, "Phone number already exists!");
//            }

            // Mã hóa mật khẩu
            String password = passwordEncoder.encode(userRegisterDTO.getPassword());

            // Lấy role cho user

            RoleEntity role = roleRepository.getRoleCustomer();
            if(role == null || role.getRoleName().isEmpty()){
              //  throw new PracticeException(HttpStatus.NOT_FOUND,"cCan not get role for create");
            }
            UserEntity user = new UserEntity();
            user.setUsername(userRegisterDTO.getUsername());
            user.setPassword(password);
            user.setEmail(userRegisterDTO.getEmail());
            user.setName(userRegisterDTO.getName());
            user.setRoleEntity(role);
            user.setStatus("ACTIVE");
            user.setGender(userRegisterDTO.getGender());
            user.setAddress(userRegisterDTO.getAddress());
            user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
            user.setDob(userRegisterDTO.getDob());

            // Lưu user vào cơ sở dữ liệu
            userRepository.save(user);

            // Map user entity thành user response và trả về
            //return mapEntityToDTO.mapUserEntityToUserDTO(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public List<UserEntity> getListUser() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }
}
