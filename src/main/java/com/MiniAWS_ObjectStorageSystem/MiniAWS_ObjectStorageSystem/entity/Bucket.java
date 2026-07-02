package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Bucket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketId;


    @Column(nullable = false,updatable = false)
    private String bucketName;

    @Column(nullable = false)
    private String bucketPath;

    @CreationTimestamp
    private LocalDateTime createdAt;

    //filmetadata to be added
    //owner id to be added
}
