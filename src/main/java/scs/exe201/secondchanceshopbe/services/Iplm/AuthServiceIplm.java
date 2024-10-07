package scs.exe201.secondchanceshopbe.services.Iplm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.spi.ActionException;
import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.JwtResponse;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.AuthFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.security.JwtService;
import scs.exe201.secondchanceshopbe.services.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceIplm implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public JwtResponse authenticateUser(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            UserEntity userEntity = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new NotFoundException("user not found")
            );
            if(userEntity.getStatus().equals("BAN")){
                new ActionFailedException("CAN_LOGIN","your account has baned");
            }else if(userEntity.getStatus().equals("DELETED")){
                new ActionFailedException("CAN_LOGIN","your account has delete");
            }else if(userEntity.getStatus().equals("VERIFY")){
                new ActionFailedException("CAN_LOGIN","your account not verify please verify account");
            }           
            String token = jwtService.generateToken(authentication);
           return new JwtResponse(token);
        } catch (Exception e) {
            throw new AuthFailedException(e.getMessage());
        }

    }


  

}