package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.BucketManagementService.LocalBucketManagementService;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.UploadResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.BucketRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.FileRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.FileMetaData;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.StorageService.LocalFileStorageService;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {


    private final LocalFileStorageService localFileStorageService;
    private final LocalBucketManagementService localBucketManagementService;
    private final BucketRepository bucketRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;


    private static final Logger logger = (Logger) LoggerFactory.getLogger(FileService.class);

    @Value("${file.upload-dir}")
    private Path uploadDirectory;


    @Transactional
    public List<UploadResponseDTO> uploadFile(MultipartFile[] multipartFiles, String bucketName, @AuthenticationPrincipal AppUser user){
        ArrayList<UploadResponseDTO> responseDTOS=new ArrayList<>();
        //store the file and return here
        for(MultipartFile multipartFile:multipartFiles){
            Path targetPath=uploadDirectory.resolve(bucketName);
            String bucket;

            if(!Files.exists(targetPath)){
                bucket=localBucketManagementService.createBucket(bucketName,user).getBucketName();
            }else{
                bucket=bucketName;
            }
            String storedFilename=localFileStorageService.store(multipartFile,bucket);


            try{
                FileMetaData fileMetadata= FileMetaData.builder()
                        .originalFilename(multipartFile.getOriginalFilename())
                        .storedFilename(storedFilename)
                        .size(multipartFile.getSize())
                        .contentType(multipartFile.getContentType())
                        .path(targetPath.toString())
                        .bucket(bucketRepository.findByBucketName(bucketName).orElseThrow(()->new IllegalArgumentException("No data in db")))
                        .build();
                FileMetaData savedFileData= fileRepository.save(fileMetadata);
                responseDTOS.add(new UploadResponseDTO(savedFileData.getFileId(), savedFileData.getOriginalFilename(), savedFileData.getStoredFilename(), savedFileData.getBucket().getBucketId(),"File uploaded successfully"));
            }catch(Exception e){
                logger.error("Failed to save filemetadata to db: ");

                try{
                    Files.deleteIfExists(uploadDirectory.resolve(bucket).resolve(storedFilename));
                }catch (Exception cleanUpError){
                    throw  new RuntimeException("Failed to cleanup file from storage after db error",cleanUpError);
                }

                throw new RuntimeException("Failed to save fileMetadata to db");
            }
        }
        return responseDTOS;
    }

    public Resource downloadFile(String bucketName, String filename) {
        FileMetaData fileMetaData=fileRepository.findByBucket_BucketNameAndOriginalFilename(bucketName,filename).orElse(null);

        if(fileMetaData==null){
            throw new IllegalArgumentException("File not found!! Check credentials");
        }


        Path dirPath=Path.of(fileMetaData.getPath());
        Path filePath=dirPath.resolve(fileMetaData.getStoredFilename());

        return localFileStorageService.loadFileAsResource(filePath);


    }
}
