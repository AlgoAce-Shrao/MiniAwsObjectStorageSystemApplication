package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BucketRepository  extends JpaRepository<Bucket,Long> {

    Optional<Bucket> findByBucketName(String bucketName);
}
