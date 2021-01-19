package com.electroeing.authserver.service;

import com.electroeing.authserver.model.UserModel;
import com.electroeing.authserver.repository.PermissionRepository;
import com.electroeing.authserver.repository.UserRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class OAuthUserServiceImpl implements OAuthUserService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public OAuthUserServiceImpl(UserRepository userRepository,
                                PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Optional<UserModel> getUserDetails(String email) {
        final Optional<UserModel> user = Stream.ofNullable(userRepository.findByEmail(email))
                .map(userEntity -> UserModel.builder()
                        .id(userEntity.getId())
                        .name(userEntity.getName())
                        .email(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .build())
                .findFirst();

        user.ifPresent(
                userModel ->
                        userModel.setGrantedAuthoritiesList(
                                permissionRepository.findPermissionsByUserEmail(userModel.getEmail())
                                        .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                                        .collect(Collectors.toList()))
        );

        return user;
    }
}
