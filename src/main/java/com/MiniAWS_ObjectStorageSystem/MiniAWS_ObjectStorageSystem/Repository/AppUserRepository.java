package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository  extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUsernameAndEmail(String username, String email);

    Optional<AppUser> findByUsername(String username);
}
