package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniAwsObjectStorageSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniAwsObjectStorageSystemApplication.class, args);
	}

}
