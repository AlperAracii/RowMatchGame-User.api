package com.dreamgamescase.UserApi.controller;

import com.dreamgamescase.UserApi.model.User;
import com.dreamgamescase.UserApi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestParam("username") String username){

        return userService.create(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateLevel(@PathVariable("id") int id){
        return userService.updateLevel(id);
    }

    @GetMapping()
    public List<User> getUsers(@RequestParam("userIds") List<Integer> userIds){

        return userService.findUserById(userIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserDetail(@PathVariable("id") int id){

        return userService.findAUserById(id);
    }

    @PostMapping("/claim-reward/{id}")
    public User claimReward(@PathVariable("id") int id, @RequestParam("coin") int coin){

        return userService.claimRewardRequest(id,coin);
    }

}
