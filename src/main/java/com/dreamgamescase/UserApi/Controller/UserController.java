package com.dreamgamescase.UserApi.Controller;

import com.dreamgamescase.UserApi.Model.User;
import com.dreamgamescase.UserApi.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User create(@RequestParam("username") String username){

        return userService.CreateUserRequest(username);
    }

    @PutMapping("/{id}")
    public User updateLevel(@PathVariable("id") int id){
        return userService.UpdateLevelRequest(id);
    }

    @GetMapping()
    public List<User> getUsers(@RequestParam("userIds") List<Integer> userIds){

        return userService.findUserById(userIds);
    }

    @GetMapping("/{id}")
    public User getUserDetail(@PathVariable("id") int id){

        return userService.findAUserById(id);
    }

    @PostMapping("/claim-reward/{id}")
    public User claimReward(@PathVariable("id") int id, @RequestParam("coin") int coin){

        return userService.ClaimRewardRequest(id,coin);
    }

}
