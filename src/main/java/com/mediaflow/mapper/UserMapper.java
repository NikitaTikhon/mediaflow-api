package com.mediaflow.mapper;

import com.mediaflow.dto.response.UserMinInfoResponse;
import com.mediaflow.dto.response.UserResponse;
import com.mediaflow.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    @Mapping(target = "imageURL", source = "mainPhoto.imageURL")
    UserMinInfoResponse userToUserMinInfoResponse(User user);

    List<UserMinInfoResponse> usersToUserMinInfoResponses(List<User> users);


//    @Named("creationDate")
//    default OffsetDateTime mapCreationDate(Long createdTimestamp) {
//        return OffsetDateTime.of(LocalDateTime.ofInstant(Instant.ofEpochMilli(createdTimestamp),
//                ZoneOffset.UTC), ZoneOffset.UTC);
//    }

}
