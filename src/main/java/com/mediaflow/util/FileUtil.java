package com.mediaflow.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@UtilityClass
public class FileUtil {

    public static File multipartToFile(MultipartFile multipart) {
        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getOriginalFilename());
            multipart.transferTo(tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File imageUrlToFile(String url) {
        try {
            URL fileURL = new URL(url);
            File tempFile = File.createTempFile(System.getProperty("java.io.tmpdir") + "/image", url.substring(url.lastIndexOf('.')));
            Files.copy(fileURL.openStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
