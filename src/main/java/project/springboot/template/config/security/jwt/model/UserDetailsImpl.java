package project.springboot.template.config.security.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.springboot.template.config.constants.EAccountStatus;
import project.springboot.template.entity.Account;
import project.springboot.template.entity.Role;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private final String username;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final EAccountStatus status;
    private final Account account;
    private final String name;

    private UserDetailsImpl(Long id, String email, String password,
                            Collection<? extends GrantedAuthority> authorities,
                            EAccountStatus status, String name, Account account) {
        this.username = email;
        this.password = password;
        this.authorities = authorities;
        this.status = status;
        this.account = account;
        this.name = name;
    }

    public static UserDetailsImpl build(Account account, Role role) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getCode() == null ? "GENERAL" : role.getCode());
        authorities.add(simpleGrantedAuthority);


        String name = account.getFirstname() + account.getLastname();
        return new UserDetailsImpl(
                account.getId(),
                account.getEmail(),
                account.getPassword(),
                authorities,
                account.getStatus(),
                name,
                account);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return EAccountStatus.ACTIVATE.equals(status);
    }
}
