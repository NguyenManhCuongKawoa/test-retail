package aladintech.co.apigateway.repository;

import aladintech.co.apigateway.model.Role;
import aladintech.co.apigateway.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
