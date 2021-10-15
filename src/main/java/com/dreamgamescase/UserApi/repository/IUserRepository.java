package com.dreamgamescase.UserApi.repository;

import com.dreamgamescase.UserApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.userid = ?1")
    User findUserById (Integer id);


}
