package com.kshrd.wearekhmer.files.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@Getter
public class FileConfig implements WebMvcConfigurer{

    private final String[] imagePath = {"main", "resources","static",  "images"};
    private final Path root = Paths.get("src", imagePath);



    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        when you add this, you just run localhost:8080/images/{yourimage34342}
        registry.addResourceHandler("/images/**").addResourceLocations(this.imagePath);
    }
}
