package com.dreamgamescase.UserApi.service.data;

import com.dreamgamescase.UserApi.model.TournamentMappingResponseModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteTournamentDao implements TournamentDao{

    private static final String BASE_URL = "http://localhost:8081";
    RestTemplate restTemplate = new RestTemplate();

    /**
     *
     *For update torunament score. This method small pice of request to tounrmanet api.
     */
    @Override
    public TournamentMappingResponseModel updateTournamentScore(int userId) {

        String uri = BASE_URL + "/tournament/update-score/" + userId;
        return restTemplate.postForObject(uri,null, TournamentMappingResponseModel.class);
    }
}
