package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileMetaData,Long> {


    @Query("""
    SELECT f
    FROM FileMetaData f
    WHERE f.bucket.bucketName = :bucketName
      AND f.originalFilename = :originalFilename
""")
    Optional<FileMetaData> findByBucket_BucketNameAndOriginalFilename(@Param("bucketName") String bucketName, @Param("originalFilename") String originalFileName);
}

