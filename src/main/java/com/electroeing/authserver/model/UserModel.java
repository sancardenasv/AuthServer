package com.electroeing.authserver.model;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Builder
@ToString
public class UserModel {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Collection<GrantedAuthority> grantedAuthoritiesList;
}
