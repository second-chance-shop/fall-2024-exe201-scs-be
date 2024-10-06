package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateUserDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;

import java.util.List;

@Service
public interface UserService {
    UserResponse registerNewUser(UserRegisterDTO userRegisterPayload);

    List<UserResponse> getListUser();

    UserResponse userUpdate(UpdateUserDTO updateUserDTO);

    UserResponse userDelete(long id);

    UserResponse getUsers(String search, Pageable pageable);

    void ActiveUser(String email);

    UserResponse getUserById(long id);
}
