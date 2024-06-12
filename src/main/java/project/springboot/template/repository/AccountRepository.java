package project.springboot.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboot.template.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    List<Account> findByRole_Code(String roleCode);

}
