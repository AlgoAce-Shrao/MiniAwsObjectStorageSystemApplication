package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(
        indexes = {
                @Index(
                        name = "idx_app_user_id",
                        columnList = "userId"
                ),
                @Index(
                        name = "idx_appUserId_bucketId",
                        columnList = "userId,bucketId"
                )
        }
)
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


    @OneToMany(mappedBy = "bucket",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<FileMetaData> fileMetaData;


    @ManyToOne(optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name="userId")
    private AppUser user;
}
