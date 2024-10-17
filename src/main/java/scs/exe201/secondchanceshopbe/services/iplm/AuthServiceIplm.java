package scs.exe201.secondchanceshopbe.services.iplm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.JwtResponse;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.AuthFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.security.JwtService;
import scs.exe201.secondchanceshopbe.services.AuthService;
import scs.exe201.secondchanceshopbe.utils.Constants;

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
                () -> new NotFoundException(Constants.USER_NOT_FOUND)
            );
            if(userEntity.getStatus().equals(StatusEnum.BAN)){
                new ActionFailedException("CANNOT_LOGIN","your account has baned");
            }else if(userEntity.getStatus().equals(StatusEnum.DELETED)){
                new ActionFailedException("CANNOT_LOGIN","your account has delete");
            }else if(userEntity.getStatus().equals(StatusEnum.VERIFY)){
                new ActionFailedException("CANNOT_LOGIN","your account not verify please verify account");
            }           
            String token = jwtService.generateToken(authentication);
           return new JwtResponse(token);
        } catch (Exception e) {
            throw new AuthFailedException(e.getMessage());
        }

    }


  

}