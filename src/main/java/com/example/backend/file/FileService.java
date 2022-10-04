package com.example.backend.file;

import com.example.backend.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {
 
    @Autowired
    AppConfiguration appConfiguration;

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String filename = generateRandom();
        File target = new File(appConfiguration.getUploadPath() + "/" + filename);
        OutputStream outputStream = new FileOutputStream(target);
        byte[] base64Encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64Encoded);
        outputStream.close();
        return filename;
    }

    public String generateRandom() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void deleteFile(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
