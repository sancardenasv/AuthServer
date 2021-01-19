package com.electroeing.authserver.service;

import com.electroeing.authserver.model.UserModel;
import java.util.Optional;

public interface OAuthUserService {
    Optional<UserModel> getUserDetails(String email);
}
