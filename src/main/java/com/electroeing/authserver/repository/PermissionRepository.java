package com.electroeing.authserver.repository;

import com.electroeing.authserver.entity.PermissionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

    String QUERY =
            "SELECT DISTINCT P.id, P.name FROM permission P \n" +
            "INNER JOIN permissionToRole P_R ON P.id = P_R.permissionId\n" +
            "INNER JOIN role R ON R.id = P_R.roleId \n" +
            "INNER JOIN userToRole U_R ON U_R.roleId = R.id\n" +
            "INNER JOIN user U ON U.id = U_R.userId\n" +
            "WHERE U.email = :email";

    @Query(nativeQuery = true, value = QUERY)
    List<PermissionEntity> findPermissionsByUserEmail(@Param("email") String email);
}
