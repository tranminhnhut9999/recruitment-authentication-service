package project.springboot.template.config.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.springboot.template.config.security.jwt.model.UserDetailsImpl;
import project.springboot.template.entity.Account;
import project.springboot.template.entity.Role;
import project.springboot.template.entity.common.ApiException;
import project.springboot.template.repository.AccountRepository;
import javax.transaction.Transactional;


@Service
@Transactional
public class JwtUserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;


    public JwtUserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> ApiException.create(HttpStatus.FORBIDDEN).withMessage("user not exist, please check with admin"));
        Role role = account.getRole();
        return UserDetailsImpl.build(account, role);
    }
}
