package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.CreateBucketResponseDTO;
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


    @ExceptionHandler(BucketValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CreateBucketResponseDTO> handleBucketValidationException(BucketValidationException bucketValidationException){
        CreateBucketResponseDTO response=new CreateBucketResponseDTO();
        response.setStatus(bucketValidationException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(BucketCreationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CreateBucketResponseDTO> handleBucketCreationException(BucketCreationException bucketCreationException){
        CreateBucketResponseDTO response=new CreateBucketResponseDTO();
        response.setStatus(bucketCreationException.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(FileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CreateBucketResponseDTO> handleFileAlreadyExistsException(FileAlreadyExistsException fileAlreadyExistsException){
        CreateBucketResponseDTO response=new CreateBucketResponseDTO();
        response.setStatus(fileAlreadyExistsException.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }



    @ExceptionHandler(FileValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CreateBucketResponseDTO> handleFileValidationException(FileValidationException fileValidationException){
        CreateBucketResponseDTO response=new CreateBucketResponseDTO();
        response.setStatus(fileValidationException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}