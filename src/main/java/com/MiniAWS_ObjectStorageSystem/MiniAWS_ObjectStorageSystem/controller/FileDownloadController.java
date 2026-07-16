package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.controller;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.FileService;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/downloads")
public class FileDownloadController {

    private final FileService fileService;

    @GetMapping("/{bucketName}/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("bucketName") String bucketName, @PathVariable("filename") String filename){
        Resource resource=fileService.downloadFile(bucketName,filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);

    }
}
