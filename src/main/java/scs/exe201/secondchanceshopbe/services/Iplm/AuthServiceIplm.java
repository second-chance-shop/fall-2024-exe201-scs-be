package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.LoginDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OTPRequest;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.JwtResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.UserResponse;
import scs.exe201.secondchanceshopbe.models.entities.RoleEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.AuthFailedException;
import scs.exe201.secondchanceshopbe.models.exception.ConflictException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.RoleRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.security.JwtService;
import scs.exe201.secondchanceshopbe.services.AuthService;
import scs.exe201.secondchanceshopbe.services.SendMailService;
import scs.exe201.secondchanceshopbe.utils.DTOToEntity;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthServiceIplm implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private final SendMailService sendMailService;


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
    @Override
    public UserResponse regiterWithOTO(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new ConflictException("Username already exists!");
        }
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new ConflictException("Email already exists!");
        }
        if (userRepository.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
            throw new ConflictException("Phone already exists!");
        }



        RoleEntity role = roleRepository.getRoleCustomer();
        if(role == null) {
            throw new NotFoundException("Role not found!");
        }
        String password = passwordEncoder.encode(userRegisterDTO.getPassword());
        try {
            UserEntity userCreate = DTOToEntity.UserResponseToEntity(userRegisterDTO);
            userCreate.setStatus("ACTIVE");
            userCreate.setRoleEntity(role);
            userCreate.setPassword(password);




            userRepository.save(userCreate);
            UserResponse userResponse = EntityToDTO.UserEntityToDTO(userCreate);

            return userResponse;
        } catch (Exception e) {
            throw new ActionFailedException(String.format("Failed register user with reason: %s", e.getMessage()));
        }
    }

    @Override
    public String verifyOTO(OTPRequest otpRequest) {
        return "";
    }


}