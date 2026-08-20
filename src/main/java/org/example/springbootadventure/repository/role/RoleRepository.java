package org.example.springbootadventure.repository.role;

import java.util.Optional;
import org.example.springbootadventure.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName name);
}
