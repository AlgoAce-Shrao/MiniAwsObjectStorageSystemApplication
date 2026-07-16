package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.controller;


import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.UploadResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buckets")
public class FileUploadController {


    private final FileService fileService;

    @PostMapping("/bucket/{bucketName}/uploadFile")
    public ResponseEntity<List<UploadResponseDTO>> processUpload(@RequestParam("files") MultipartFile[] multipartFiles, @PathVariable("bucketName") String bucketName, @AuthenticationPrincipal AppUser user){
        List<UploadResponseDTO> response=fileService.uploadFile(multipartFiles,bucketName,user);
        return ResponseEntity.ok(response);
    }
}
