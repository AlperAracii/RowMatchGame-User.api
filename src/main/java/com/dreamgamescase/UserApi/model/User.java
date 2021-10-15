package com.dreamgamescase.UserApi.model;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    @Column(name = "username")
    private String username;

    @Column(name = "level")
    private Integer level;

    @Column(name = "coin")
    private Integer coin;

    @Column(name = "segment")
    private Integer segment;

}
