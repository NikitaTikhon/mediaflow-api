package com.mediaflow.mapper;

import com.mediaflow.dto.response.PhotoResponse;
import com.mediaflow.entity.MainPhoto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MainPhotoMapper {

    PhotoResponse mainPhotoToPhotoResponse(MainPhoto mainPhoto);

}
