package com.jiyeon.project.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

public class IndexUtil {
    private static final Logger LOG = LoggerFactory.getLogger(IndexUtil.class);

    public static String loadAsString(String path){
        try{
            File file = new ClassPathResource(path).getFile();

            return new String(Files.readAllBytes(file.toPath()));
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
