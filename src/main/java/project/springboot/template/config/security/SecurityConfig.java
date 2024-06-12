package project.springboot.template.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.springboot.template.config.security.jwt.JwtAuthenticationEntryPoint;
import project.springboot.template.config.security.jwt.JwtFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtFilter jwtFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

//        http.anonymous().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // permit all swagger api
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // permit all login api
        http.authorizeRequests().antMatchers("/api/auth/login").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/logout").permitAll();
        http.authorizeRequests().antMatchers("/api/accounts/register").permitAll();

        http.authorizeRequests().antMatchers("/api/page-content/**").permitAll();

        http.authorizeRequests().antMatchers("/ws/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();
        // config oauth2 resource
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

    }


}
