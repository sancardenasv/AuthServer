package com.electroeing.authserver.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CustomUser extends User {
    private Long id;
    private String name;

    public CustomUser(UserModel userModel) {
        super(userModel.getEmail(), userModel.getPassword(), userModel.getGrantedAuthoritiesList());
        this.id = userModel.getId();
        this.name = userModel.getName();
    }
}
