package scs.exe201.secondchanceshopbe.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;

import java.util.List;

@Service
public interface UserService {
    ResponseEntity<?> registerNewUser(UserRegisterDTO userRegisterPayload);

    List<UserEntity> getListUser();
}
