package com.dreamgamescase.UserApi.service;

import com.dreamgamescase.UserApi.model.TournamentMappingResponseModel;
import com.dreamgamescase.UserApi.model.User;
import com.dreamgamescase.UserApi.repository.IUserRepository;
import com.dreamgamescase.UserApi.service.data.TournamentDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    private UserService userService;

    private IUserRepository userRepository;
    private TournamentDao tournamentDao;

    @Before
    public void setUp() throws Exception {

        userRepository = Mockito.mock(IUserRepository.class);
        tournamentDao = Mockito.mock(TournamentDao.class);

        userService = new UserService(userRepository, tournamentDao);
    }

    @Test
    public void whenCreateUserCalledWithValidRequest_itShouldReturnValidUserDetail() {
        String username = "AlperA";

        User userRequest = new User();
        userRequest.setUsername(username);
        userRequest.setLevel(1);
        userRequest.setCoin(5000);
        userRequest.setSegment(0);

        User user = new User();
        user.setUserid(123);
        user.setUsername(userRequest.getUsername());
        user.setLevel(userRequest.getLevel());
        user.setCoin(userRequest.getCoin());
        user.setSegment(userRequest.getSegment());

        Mockito.when(userRepository.save(userRequest)).thenReturn(user);

        ResponseEntity<User> result = userService.create(username);

        Assert.assertEquals(result, ResponseEntity.accepted().body(user));
        Mockito.verify(userRepository).save(userRequest);
    }

    @Test
    public void whenCreateUserCalledWithBadRequest_itShouldReturnErrorMessage() {
        String username = "";

        User userRequest = new User();
        userRequest.setUsername(username);
        userRequest.setLevel(1);
        userRequest.setCoin(5000);
        userRequest.setSegment(0);

        ResponseEntity<User> result = userService.create(username);

        Assert.assertEquals(result, new ResponseEntity("Username cannot be empty", HttpStatus.BAD_REQUEST));
    }

    @Test
    public void whenUpdateUserCalledWithValidRequest_itShouldReturnUpdatedUserData() {
        int userId = 5;
        User user = new User();
        user.setUserid(userId);
        user.setUsername("username");
        user.setLevel(1);
        user.setCoin(5000);
        user.setSegment(0);

        User updateUser = new User();
        updateUser.setUserid(user.getUserid());
        updateUser.setUsername(user.getUsername());
        updateUser.setLevel(user.getLevel() + 1);
        updateUser.setCoin(user.getCoin() + 25);
        updateUser.setSegment(user.getSegment());

        TournamentMappingResponseModel response = new TournamentMappingResponseModel();
        response.setId(111);
        response.setGroupid(222);
        response.setUserid(userId);
        response.setUsername(user.getUsername());
        response.setScore(2);
        response.setIsactive(true);
        response.setIsClaimed(false);

        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(updateUser);
        Mockito.when(tournamentDao.updateTournamentScore(userId)).thenReturn(response);

        ResponseEntity<User> result = userService.updateLevel(userId);
        Assert.assertEquals(result, updateUser);
    }

    @Test
    public void whenUpdateUserCalledWithNullUserRequest_itShouldReturnErrorData() {
        int userId = 0;

        ResponseEntity<User> result = userService.updateLevel(userId);
        Assert.assertEquals(result, new ResponseEntity("User cannot find with id:" + userId, HttpStatus.BAD_REQUEST));
    }

    @Test
    public void whenFindUserByIdCalledWithValidRequest_itShouldReturnUserData() {
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(3);
        userIds.add(5);
        userIds.add(10);

        User user = new User();
        user.setUserid(123);
        user.setUsername("username");
        user.setLevel(1);
        user.setCoin(5000);
        user.setSegment(0);

        List<User> users = new ArrayList<>();
        for (Integer id : userIds) {
            user.setUserid(id);
            users.add(user);
            Mockito.when(userRepository.findUserById(id)).thenReturn(user);
        }


        List<User> result = userService.findUserById(userIds);
        Assert.assertEquals(result, users);
    }

    @Test
    public void whenClaimRewardCalledWithValidRequest_itShouldReturnUserData() {
        int userId = 5;
        int coin = 5000;

        User user = new User();
        user.setUserid(userId);
        user.setUsername("username");
        user.setLevel(1);
        user.setCoin(5000);
        user.setSegment(0);

        User updateUser = new User();
        updateUser.setUserid(userId);
        updateUser.setUsername("username");
        updateUser.setLevel(1);
        updateUser.setCoin(5000 + coin);
        updateUser.setSegment(0);

        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(updateUser);

        User result = userService.claimRewardRequest(userId, coin);
        Assert.assertEquals(result, updateUser);
    }

    @Test
    public void whenClaimRewardCalledWithNoUserIdRequest_itShouldReturnUserData() {
        int userId = 0;
        int coin = 25;

        Assert.assertThrows("User not found", Exception.class, () -> userService.claimRewardRequest(userId, coin));
    }

}