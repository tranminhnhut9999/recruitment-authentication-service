package project.springboot.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboot.template.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByCode(String code);
}