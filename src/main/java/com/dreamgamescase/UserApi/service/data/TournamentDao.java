package com.dreamgamescase.UserApi.service.data;

import com.dreamgamescase.UserApi.model.TournamentMappingResponseModel;

public interface TournamentDao {

    TournamentMappingResponseModel updateTournamentScore(int userId);
}
