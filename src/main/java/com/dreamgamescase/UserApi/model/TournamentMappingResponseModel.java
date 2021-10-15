package com.dreamgamescase.UserApi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentMappingResponseModel {

    private Integer id;
    private Integer groupid;
    private Integer userid;
    private String username;
    private Integer score;
    private Boolean isClaimed;
    private Boolean isactive;
}
