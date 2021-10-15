package com.dreamgamescase.UserApi.service;

import com.dreamgamescase.UserApi.model.User;
import com.dreamgamescase.UserApi.repository.IUserRepository;
import com.dreamgamescase.UserApi.service.data.TournamentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final TournamentDao tournamentDao;

    @Autowired
    public UserService(IUserRepository userRepository, TournamentDao tournamentDao) {
        this.userRepository = userRepository;
        this.tournamentDao = tournamentDao;
    }

    /**
     *
     * @param username -> Input taken from user
     * @return created user Model
     * If no username return message.
     */
    public ResponseEntity<User> create(String username) {
        if (username != "") {
            User user = new User();
            user.setUsername(username);
            user.setLevel(1);
            user.setCoin(5000);
            user.setSegment(0);

            User create = userRepository.save(user);
            return ResponseEntity.accepted().body(create);
        } else
            return new ResponseEntity("Username cannot be empty", HttpStatus.BAD_REQUEST);

    }

    /**
     *
     * @param userId -> Input for update which user
     * @return updated user data
     * If any user with userId return cannot find message.
     */
    public ResponseEntity<User> updateLevel(int userId) {

        User user = userRepository.findUserById(userId);
        if (user != null) {
            user.setLevel(user.getLevel() + 1);
            user.setCoin(user.getCoin() + 25);

            //Create segment colomn for group user in same segment in tournament.
            // This way helps us to easy defined groups segments.
            if (user.getLevel() >= 20) {
                int segment = (user.getLevel() / 100) + 1;
                user.setSegment(segment);
            }
            User update = userRepository.save(user);

            //Send request tournament api for update tournament score +1
            tournamentDao.updateTournamentScore(userId);
            return ResponseEntity.accepted().body(update);
        } else
            return new ResponseEntity("User cannot find with id:"+ userId, HttpStatus.BAD_REQUEST);

    }

    /**
     *
     * @param userIds -> Come from tournament api LEaderBoard service
     * @return users info
     *  tournamentApi send userIds for users informations. Api use these infos for leader board.
     */
    public List<User> findUserById(List<Integer> userIds) {

        List<User> result = new ArrayList<User>();
        userIds.forEach((aa) ->
                result.add(userRepository.findUserById(aa))
        );
        return result;
    }

    /**
     *
     * @param id --> coma from tournament api for one user.
     * @return one user
     * This method return one user detail.
     */
    public ResponseEntity<User> findAUserById(int id) {
        User user = userRepository.findUserById(id);
        if (user != null)
            return ResponseEntity.accepted().body(user);
        else
            return new ResponseEntity("User not found id:" +id, HttpStatus.BAD_REQUEST);

    }

    /**
     *
     * @param userId --> For which user claim reward
     * @param coin --> Rewarded coin by rank
     * @return user's data
     * This method update users coin with rewarded coin.
     */
    public User claimRewardRequest(int userId, int coin) {
        try {
            ResponseEntity<User> response = findAUserById(userId);
            User user = response.getBody();
            user.setCoin(user.getCoin() + coin);
            return userRepository.save(user);
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

    }
}
