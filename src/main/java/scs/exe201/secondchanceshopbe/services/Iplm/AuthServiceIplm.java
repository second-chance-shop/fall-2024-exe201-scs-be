package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.respones.JwtResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.JwtResponse;
import scs.exe201.secondchanceshopbe.models.exception.AuthFailedException;
import scs.exe201.secondchanceshopbe.security.JwtService;
import scs.exe201.secondchanceshopbe.services.AuthService;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceIplm implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public JwtResponse authenticateUser(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            String token = jwtService.generateToken(authentication);
           return new JwtResponse(token);
        } catch (Exception e) {
            throw new AuthFailedException(e.getMessage());
        }

    }

}