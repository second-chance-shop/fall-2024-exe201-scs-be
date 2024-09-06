package scs.exe201.secondchanceshopbe.services;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.respones.JwtResponse;

@Service
public interface AuthService {
    JwtResponse authenticateUser(LoginDTO loginDto);
}
