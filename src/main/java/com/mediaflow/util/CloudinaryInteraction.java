package com.mediaflow.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.mediaflow.config.constant.CloudinaryConstant;
import com.mediaflow.config.constant.PhotoConstant;
import com.mediaflow.exception.PhotoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class CloudinaryInteraction {

    private Cloudinary cloudinary;

    public Map<String, String> savePhoto(File file, String userId) {
        try {
            Map uploadedFile = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap(
                            "folder", CloudinaryConstant.PHOTO_PATH.formatted(userId),
                            "transformation", new Transformation().quality(100)));

            HashMap<String, String> fileInfo = new HashMap<>();

            fileInfo.put("url", String.valueOf(uploadedFile.get("secure_url")));
            String publicIdPath = String.valueOf(uploadedFile.get("public_id"));
            fileInfo.put("publicId", publicIdPath.substring(publicIdPath.lastIndexOf("/") + 1));

            return fileInfo;
        } catch (IOException e) {
            throw new PhotoException(PhotoConstant.PHOTO_NOT_SAVED, e);
        }
    }

    public void deletePhoto(String publicId, String userId) {
        try {
            cloudinary.uploader().destroy(CloudinaryConstant.PHOTO_DELETE_PATH.formatted(userId, publicId),
                    ObjectUtils.asMap("invalidate", true));
        } catch (IOException e) {
            throw new PhotoException(PhotoConstant.PHOTO_NOT_DELETED, e);
        }
    }

}
