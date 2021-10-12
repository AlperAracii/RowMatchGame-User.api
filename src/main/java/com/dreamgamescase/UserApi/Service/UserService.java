package com.dreamgamescase.UserApi.Service;

import com.dreamgamescase.UserApi.Model.TournamentMappingResponseModel;
import com.dreamgamescase.UserApi.Model.User;
import com.dreamgamescase.UserApi.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    RestTemplate restTemplate = new RestTemplate();
    private final IUserRepository _userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    public User CreateUserRequest(String username){
        User user = new User();
        user.setUsername(username);
        user.setLevel(1);
        user.setCoin(5000);
        user.setSegment(0);

        User create = _userRepository.save(user);

        return create;
    }

    public User UpdateLevelRequest(int id){
        User user = _userRepository.findUserById(id);

        user.setLevel(user.getLevel() + 1);
        user.setCoin(user.getCoin() + 25);

        if(user.getLevel() >= 20) {
            int segment = (user.getLevel() / 100) + 1;
            user.setSegment(segment);
        }
        User update = _userRepository.save(user);

        String uri = "http://localhost:8081/tournament/UpdateTournamentScore/" + id;
        TournamentMappingResponseModel result = restTemplate.getForObject(uri, TournamentMappingResponseModel.class);

        return user;
    }

    public List<User> findUserById(List<Integer> userids){

        List<User> result = new ArrayList<User>();
        userids.stream().forEach((aa) ->
                result.add(_userRepository.findUserById(aa))
        );
        return result;
    }

    public User findAUserById(int id){

        User user = _userRepository.findUserById(id);
        return user;
    }

    public User ClaimRewardRequest(int userid, int coin){
        User user = _userRepository.findUserById(userid);
        user.setCoin(user.getCoin() + coin);
        return _userRepository.save(user);
    }
}
