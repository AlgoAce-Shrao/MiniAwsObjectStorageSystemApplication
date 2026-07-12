package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String jwt;

    private Long userId;

    private String status;
}
