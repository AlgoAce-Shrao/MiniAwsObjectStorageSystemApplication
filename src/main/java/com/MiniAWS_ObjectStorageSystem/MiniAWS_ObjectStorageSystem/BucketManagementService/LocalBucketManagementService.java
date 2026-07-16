package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.BucketManagementService;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.BucketCreationException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception.BucketValidationException;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.AppUserRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.BucketRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Slf4j
@RequiredArgsConstructor
public class LocalBucketManagementService implements BucketManagementService{

    private static final Logger logger = LoggerFactory.getLogger(LocalBucketManagementService.class);
    private static final String SAFE_BUCKET_NAME_PATTERN = "^[a-zA-Z0-9_-]+$";


    @Value("${file.upload-dir}")
    private Path uploadDirectory;

    private final BucketRepository bucketRepository;
    private final AppUserRepository appUserRepository;

    private void validateBucketName(String bucketName) {
        if (bucketName == null || bucketName.trim().isEmpty()) {
            throw new BucketValidationException("Bucket name cannot be null or empty");
        }

        if (!bucketName.matches(SAFE_BUCKET_NAME_PATTERN)) {
            throw new BucketValidationException(
                    "Bucket name can only contain alphanumeric characters, hyphens, and underscores");
        }

        // Ensure the resolved path is still within the upload directory
        Path targetPath = uploadDirectory.resolve(bucketName).normalize();
        if (!targetPath.startsWith(uploadDirectory.normalize())) {
            throw new BucketValidationException("Invalid bucket name: path traversal detected");
        }
    }


    @Override
    public Bucket createBucket(String bucketName, AppUser user) {
        validateBucketName(bucketName);
        Path targetPath = uploadDirectory.resolve(bucketName).normalize();

        // Create directory
        try {

            if(Files.exists(targetPath)){
                throw new BucketCreationException(
                        "Bucket already exists"
                );
            }

            Files.createDirectory(targetPath);
        }catch(FileAlreadyExistsException e) {
            logger.warn("Bucket of the same name already exists: {}", bucketName);
            throw new BucketCreationException("Bucket already exists: " + bucketName);
        }catch (IOException e) {
            logger.warn("Failed to create bucket directory: {}", targetPath, e);
            throw new BucketCreationException("Failed to create bucket directory");
        }

        // Create database record
        try {
            Bucket newBucket = Bucket.builder()
                    .bucketName(bucketName)
                    .bucketPath(targetPath.toString())
                    .user(user)
                    .build();
            return bucketRepository.save(newBucket);
        } catch (Exception e) {
            logger.warn("Failed to save bucket to database: {}", bucketName, e);
            // Attempt cleanup
            try {
                Files.deleteIfExists(targetPath);
            } catch (IOException cleanupError) {
                logger.warn("Failed to cleanup bucket directory after DB error: {}",
                        targetPath, cleanupError);
            }
            throw new BucketCreationException("Failed to create bucket");
        }
    }
}
