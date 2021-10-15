package com.dreamgamescase.UserApi.service;

import com.dreamgamescase.UserApi.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    ResponseEntity<User> create(String username);
    ResponseEntity<User> updateLevel(int id);
    List<User> findUserById(List<Integer> userIds);
    ResponseEntity<User> findAUserById(int id);
    User claimRewardRequest(int userid, int coin);


}
