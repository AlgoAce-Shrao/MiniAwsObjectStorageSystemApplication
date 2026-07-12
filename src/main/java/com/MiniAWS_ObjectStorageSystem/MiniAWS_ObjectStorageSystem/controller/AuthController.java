package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.controller;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.LoginRequestDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.LoginResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.SignUpRequestDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.SignUpResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.InvalidPasswordException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUpUser(@RequestBody SignUpRequestDTO signUpRequestDTO) throws InvalidPasswordException {
        SignUpResponseDTO response=authService.signUpUser(signUpRequestDTO);
        response.setStatus("User created successfully");
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.loginUser(loginRequestDTO));
    }
}
