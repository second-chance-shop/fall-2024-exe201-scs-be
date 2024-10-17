package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;

import java.util.List;

@Service
public interface UserService {

    List<UserResponse> getListUser();

    UserResponse userDelete(long id);

    UserResponse getUsers(String search, Pageable pageable);

    void ActiveUser(String email);

    UserResponse getUserById(long id);

    UserResponse getUserProfile();

    UserResponse registerUser(UserRegisterDTO userRegisterDTO, MultipartFile image);

    UserResponse updateUser(UserUpdateDTO updateUserDTO, MultipartFile image);

    UserResponse changPassword(String email,String oldPassword, String newPassword, String newPasswordConfirm);
    void setPassword(String email, String password);
}
