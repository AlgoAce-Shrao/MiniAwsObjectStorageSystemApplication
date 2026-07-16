package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BucketRepository  extends JpaRepository<Bucket,Long> {

    Optional<Bucket> findByBucketName(String bucketName);

//    @Query(value = "SELECT b.bucketId,b.bucketName FROM bucket b RIGHT JOIN  AppUser a WHERE b.userId=:a.userId")
    List<Bucket> findAllByUser_UserId(Long userId);

    @Query("""
SELECT b
FROM Bucket b
JOIN b.user u
WHERE u.userId = :userId
AND b.bucketId = :bucketId
""")
    Optional<Bucket> findByUser_userIdAndBucketId(@Param("userId") Long userId, @Param("bucketId")Long bucketId);
}