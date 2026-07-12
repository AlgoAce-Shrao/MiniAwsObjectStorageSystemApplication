package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.LoginResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.SignUpResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<SignUpResponseDTO> handleUserAlreadyExists(UserAlreadyExistsException existsException){
        SignUpResponseDTO response=new SignUpResponseDTO();
        response.setStatus(existsException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SignUpResponseDTO> handlePasswordValidations(InvalidPasswordException invalidPasswordException){
        SignUpResponseDTO response=new SignUpResponseDTO();
        response.setStatus(invalidPasswordException .getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<LoginResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        LoginResponseDTO response=new LoginResponseDTO();
        response.setStatus(usernameNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
