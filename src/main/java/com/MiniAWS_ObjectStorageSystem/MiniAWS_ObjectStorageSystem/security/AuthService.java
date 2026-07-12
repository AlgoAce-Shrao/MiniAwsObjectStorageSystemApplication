package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.security;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.LoginRequestDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.LoginResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.SignUpRequestDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.SignUpResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.InvalidPasswordException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.UserAlreadyExistsException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.UsernameNotFoundException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.AppUserRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;



    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])" +        // at least one lowercase
                    "(?=.*[A-Z])" +         // at least one uppercase
                    "(?=.*\\d)" +           // at least one digit
                    "(?=.*[@$!%*?&])" +     // at least one special character
                    "[A-Za-z\\d@$!%*?&]{8,64}$"; // 8-64 chars, no spaces

    private void validatePassword(String password) throws InvalidPasswordException {

        if (password == null || password.isBlank()) {
            throw new InvalidPasswordException("Password is required.");
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, be 8-64 characters long, and contain no spaces."
            );
        }
    }

    public SignUpResponseDTO signUpUser(SignUpRequestDTO signUpRequestDTO) {
        //logic:
        // user enter the details
        //check if the user already exists in the db or not
        //if not then move forward else throw error->userAlready exists exception
        // moving forward:
        //save the user data to the db and return response with status
        AppUser appUser=appUserRepository.findAppUserByUsernameAndEmail(signUpRequestDTO.getUsername(),signUpRequestDTO.getEmail());

        if(appUser!=null){
            throw new UserAlreadyExistsException("User already exists!!");
        }

        // password validation
//        try {
//            validatePassword(signUpRequestDTO.getPassword());
//        } catch (InvalidPasswordException e) {
//            throw new RuntimeException(e);
//        }

        appUser=appUserRepository.save(AppUser.builder()
                .username(signUpRequestDTO.getUsername())
                .email(signUpRequestDTO.getEmail())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                .build()
        );

        return modelMapper.map(appUser,SignUpResponseDTO.class);

    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword())
                );

        AppUser appUser =(AppUser)authentication.getPrincipal();

        String token= null;
        if (appUser != null) {
            token = authUtil.generateToken(appUser);
        }else {
            throw new UsernameNotFoundException("Username not found");
        }

        return new LoginResponseDTO(token, appUser.getUserId(), "User logged in successfully");


    }
}
