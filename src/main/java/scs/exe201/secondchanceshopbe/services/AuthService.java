package scs.exe201.secondchanceshopbe.services;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPRequest;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.JwtResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;

@Service
public interface AuthService {
    JwtResponse authenticateUser(LoginDTO loginDto);

    UserResponse regiterWithOTO(UserRegisterDTO registerDTO);

    String verifyOTO(OTPRequest otpRequest);
}
