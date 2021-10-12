package com.dreamgamescase.UserApi.Service;

import com.dreamgamescase.UserApi.Model.User;
import com.dreamgamescase.UserApi.Repository.IUserRepository;

import java.util.List;

public interface IUserService {

    User CreateUserRequest(String username);
    User UpdateLevelRequest(int id);
    List<User> findUserById(List<Integer> userids);
    User findAUserById(int id);
    User ClaimRewardRequest(int userid, int coin);


}
