package project.springboot.template.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import project.springboot.template.config.security.AuthoritiesConstants;
import project.springboot.template.config.security.jwt.model.UserDetailsImpl;
import project.springboot.template.entity.Account;
import project.springboot.template.entity.common.ApiException;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static void ensureCurrentUserHasAuthority(String authority) {
        if (!isCurrentUserHasAuthority(authority)) {
            throw ApiException.create(HttpStatus.FORBIDDEN);
        }
    }

    /*TODO: implement your logic here for get User instant in System*/
    public static Optional<Account> getCurrentUserAccountLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();

        Account account = null;

        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            account = userDetails.getAccount();
        }
        return Optional.ofNullable(account);
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUsernameLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }


    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean isCurrentUserHasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                getAuthorities(authentication).anyMatch(authority::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority);
    }

}