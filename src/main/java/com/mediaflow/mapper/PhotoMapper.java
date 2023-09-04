package com.mediaflow.mapper;

import com.mediaflow.dto.response.PhotoResponse;
import com.mediaflow.entity.Photo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoResponse photoToPhotoResponse(Photo photo);

    List<PhotoResponse> photosToPhotoResponses(List<Photo> photos);

}
