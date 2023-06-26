package com.kshrd.wearekhmer.files.service.serviceImplement;

import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileServiceImpl implements IFileService {

    private final FileConfig fileConfig;
    private final ServiceClassHelper serviceClassHelper;
    Path root;

    public FileServiceImpl(FileConfig fileConfig, ServiceClassHelper serviceClassHelper) {
        this.fileConfig = fileConfig;
        this.serviceClassHelper = serviceClassHelper;
    }


    @PostConstruct
    private void init() {
        this.root = fileConfig.getRoot();
    }


    @Override
    public String uploadFile(MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        if (isFileNameContains(fileName)) {
            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);


            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            Files.copy(multipartFile.getInputStream(), this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

//            String serverName = request.getScheme() + "://" + request.getServerName();

//            System.out.println(serverName); // Output: http://localhost:8080

//            String plusURL = serverName + "/api/v1/files/file/filename?name=" + fileName;
//            System.out.println(plusURL);
            return "http://localhost:8080/api/v1/files/file/filename?name=" + fileName;
        }

        return null;
    }


    @Override
    public String uploadFileV2(MultipartFile multipartFile, String imageType, String primaryId) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        if (isFileNameContains(fileName)) {
            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);


            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            Files.copy(multipartFile.getInputStream(), this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            String plusURL = "http://localhost:8080/api/v1/files/file/filename?name=" + fileName;
            String message = serviceClassHelper.uploadImageToSpecificTable(imageType, plusURL, primaryId);

            return plusURL;
        }

        return null;
    }

    boolean isFileNameContains(String fileName) {
        return
                fileName.contains(".jpeg") ||
                        fileName.contains(".png") ||
                        fileName.contains(".jpg") ||
                        fileName.contains(".docx") ||
                        fileName.contains(".pptx") ||
                        fileName.contains(".gif");
    }

    @Override
    public ResponseEntity<?> getFile(String fileName) throws IOException {

        Path imagePath = Paths.get(this.root + "/" + fileName);
        if (!Files.exists(imagePath)) {
            throw new ValidateException("filename not found!", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
            try {
                ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(imagePath));
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(byteArrayResource.getInputStream()));
            } catch (Exception ex) {
                throw new IOException("File not found!");
            }

        }


    @Override
    public ResponseEntity<?> uploadMultiFile(MultipartFile[] files) throws IOException {
        return null;
    }


    @Override
    public void deleteFileByFileName(String fileName) {
        try {
            final String[] imagePath = {"main", "resources", "static", "images"};
            final Path root = Paths.get("src", imagePath);

            Path file = root.resolve(fileName);
            boolean deleteFile = Files.deleteIfExists(file);
            if (deleteFile) {
                System.out.println("File was delete.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
