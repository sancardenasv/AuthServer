package com.electroeing.authserver.repository;

import com.electroeing.authserver.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    ClientEntity findByClientId(String clientId);
}
