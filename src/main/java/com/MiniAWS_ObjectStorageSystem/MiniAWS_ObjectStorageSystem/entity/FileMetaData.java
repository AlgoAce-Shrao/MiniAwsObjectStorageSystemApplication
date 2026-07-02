package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(nullable = false,updatable = false)
    private String originalFilename;


    @Column(unique = true,nullable = false)
    private String storedFilename;

    private String path;

    //bucket to be added

    @CreationTimestamp
    private LocalDateTime createdAt;
}
