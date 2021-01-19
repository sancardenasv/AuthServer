package com.electroeing.authserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class ClientEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "clientId")
    private String clientId;
    @Column(name = "authorities")
    private String authorities;
    @Column(name = "authorizedGrantTypes")
    private String authorizedGrantTypes;
    @Column(name = "autoApproveScopes")
    private String autoApproveScopes;
    @Column(name = "redirectUri")
    private String redirectUri;
    @Column(name = "scopes")
    private String scopes;
    @Column(name = "secret")
    private String secret;

}
