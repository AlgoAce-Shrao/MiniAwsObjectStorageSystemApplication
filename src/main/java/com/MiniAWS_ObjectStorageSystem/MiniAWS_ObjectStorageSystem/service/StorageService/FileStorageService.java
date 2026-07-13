package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.StorageService;


import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public interface FileStorageService {

    String store(MultipartFile multipartFile,String bucketName);

    Resource loadFileAsResource(Path filePath);
}
