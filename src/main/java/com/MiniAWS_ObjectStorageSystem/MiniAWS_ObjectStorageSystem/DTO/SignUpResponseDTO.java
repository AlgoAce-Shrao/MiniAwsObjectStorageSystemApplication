package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDTO {

    private String username;

    private String email;

    private String status;
}
