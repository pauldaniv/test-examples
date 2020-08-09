package com.paul.spark.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class ResourceResolver implements Serializable {
    public String resolve(final String path) {
        String storageDir = System.getenv("SPARK_APP_STORAGE_DIR");
        if (storageDir == null) {
            storageDir = System.getProperty("user.home") + "/data-sets";
            log.info("Could not find SPARK_APP_STORAGE_DIR value...");
            log.info("Using default: {}", storageDir);
        }
        return String.format("%s/%s/", storageDir, path);
    }
}
