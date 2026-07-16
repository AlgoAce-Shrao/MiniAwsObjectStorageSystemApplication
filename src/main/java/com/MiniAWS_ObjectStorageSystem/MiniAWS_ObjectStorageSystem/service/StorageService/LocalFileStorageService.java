package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.StorageService;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.FileNotLoadedException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.FileValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService{

    @Value("${file.upload-dir}")
    private Path uploadDirectory;


    @Override
    public String store(MultipartFile multipartFile, String bucketName) {
        //prepare the storedfilename which is unique
        //take the path of the storage bucket--> resolve it with the storedFilename
        //implement saving the file properly and returning the storedFilename

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new FileValidationException("Valid Name required");
        }

        // derive extension from original filename if present
        String extension = "";
        int idx = originalFilename.lastIndexOf('.');
        if (idx >= 0) {
            extension = originalFilename.substring(idx);
        } else {
            // fallback to content type (e.g., image/png -> .png). map jpeg -> jpg
            String ct = multipartFile.getContentType();
            if (ct != null && ct.contains("/")) {
                String sub = ct.substring(ct.indexOf('/') + 1);
                if ("jpeg".equalsIgnoreCase(sub)) sub = "jpg";
                extension = "." + sub;
            }
        }

        String storedFilename = UUID.randomUUID().toString() + extension;

        Path targetPath = uploadDirectory.resolve(bucketName).resolve(storedFilename);

        try {
            Files.createDirectories(targetPath.getParent());
            multipartFile.transferTo(targetPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the file:" + e);
        }

        return storedFilename;
    }

    @Override
    public Resource loadFileAsResource(Path filePath) {

        try{
            Resource resource=new UrlResource(filePath.toUri());

            if(resource.exists() && resource.isReadable()){
                return resource;
            }
        } catch (Exception e) {
            throw new FileNotLoadedException("Failed to load the file as resource:" + e);
        }
        throw new FileNotLoadedException("File not found or not readable");
    }
}
