package project.springboot.template.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import project.springboot.template.config.security.jwt.JwtUtil;
import project.springboot.template.config.security.jwt.model.UserDetailsImpl;
import project.springboot.template.dto.request.JwtRequest;
import project.springboot.template.dto.response.JwtResponse;
import project.springboot.template.entity.Account;
import project.springboot.template.entity.common.ApiException;
import project.springboot.template.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JwtService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    public JwtService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public JwtResponse login(JwtRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            throw ApiException.create(HttpStatus.FORBIDDEN).withMessage("account was locked");
        } catch (BadCredentialsException e) {
            throw ApiException.create(HttpStatus.FORBIDDEN).withMessage("user name or password is incorrect");
        } catch (AuthenticationException e) {
            throw ApiException.create(HttpStatus.FORBIDDEN).withMessage("Invalid username or password");
        }
        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getUsername());
        Account account = userDetails.getAccount();
        final String jwtToken = jwtUtil.generateToken(userDetails, false);
        JwtResponse response = new JwtResponse();
        response.setToken(jwtToken);
        response.setAccountId(account.getId());
        response.setEmail(userDetails.getUsername());
        response.setName(userDetails.getName());
        response.setRoleCodes(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String avatarImage = account.getAvatarImg();
        if (avatarImage != null) {
            response.setAvatarImageUrl(avatarImage);
        }
        return response;
    }
}
