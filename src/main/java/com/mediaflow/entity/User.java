package com.mediaflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
@Entity(name = "user")
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "userPhotos")
    private List<Photo> photos;

    @OneToOne(mappedBy = "userMainPhoto")
    private MainPhoto mainPhoto;

    @OneToMany(mappedBy = "userSender")
    private List<Follower> followsSend;

    @OneToMany(mappedBy = "userReceiver")
    private List<Follower> followsReceived;

    public User(String id, String username, MainPhoto mainPhoto) {
        this.id = id;
        this.username = username;
        this.mainPhoto = mainPhoto;
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
