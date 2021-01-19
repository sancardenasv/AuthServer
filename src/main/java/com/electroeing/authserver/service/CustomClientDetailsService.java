package com.electroeing.authserver.service;

import com.electroeing.authserver.repository.ClientRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailsService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    @Value("${config.oauth2.access-token-validity}")
    private int accessTokenValidity;
    @Value("${config.oauth2.refresh-token-validity}")
    private int refreshTokenValidity;

    public CustomClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {

        return Optional.ofNullable(clientRepository.findByClientId(clientId)).map(client -> {
            BaseClientDetails details =
                    new BaseClientDetails(client.getClientId(), null, client.getScopes(),
                    client.getAuthorizedGrantTypes(), client.getAuthorities());
            details.setClientSecret(client.getSecret());
            details.setRegisteredRedirectUri(Collections.singleton(client.getRedirectUri()));
            details.setAutoApproveScopes(Arrays.asList(client.getAutoApproveScopes().split(",")));
            details.setAccessTokenValiditySeconds(accessTokenValidity);
            details.setRefreshTokenValiditySeconds(refreshTokenValidity);
            return details;
        }).orElseThrow(() -> new ClientRegistrationException("Client " + clientId + "not found"));

    }
}
