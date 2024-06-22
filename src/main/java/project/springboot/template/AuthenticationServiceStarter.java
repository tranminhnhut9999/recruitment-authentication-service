package project.springboot.template;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.NotFoundException;
import project.springboot.template.config.constants.EAccountStatus;
import project.springboot.template.entity.Account;
import project.springboot.template.entity.Role;
import project.springboot.template.repository.AccountRepository;
import project.springboot.template.repository.RoleRepository;

import java.util.List;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@OpenAPIDefinition(
        info = @Info(title = "Apply Default Global SecurityScheme in springdoc-openapi", version = "1.0.0"),
        security = {@SecurityRequirement(name = "Bearer Authentication")})
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class AuthenticationServiceStarter {
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceStarter(RoleRepository roleRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceStarter.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setInitData() {
        setInitRoles();
        setInitAdminAccount();
    }

    public void setInitRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {

            Role adminRole = new Role();
            adminRole.setName("Admin");
            adminRole.setCode("ADMIN");

            Role hrManagerRole = new Role();
            hrManagerRole.setName("HR Manager");
            hrManagerRole.setCode("HR_MANAGER");

            Role hrStaffRole = new Role();
            hrStaffRole.setName("HR Staff");
            hrStaffRole.setCode("HR_STAFF");

            Role departmentRole = new Role();
            departmentRole.setName("Department");
            departmentRole.setCode("DEPARTMENT");

            roles.add(adminRole);
            roles.add(hrManagerRole);
            roles.add(hrStaffRole);
            roles.add(departmentRole);
            roleRepository.saveAllAndFlush(roles);
        }
    }

    public void setInitAdminAccount() {
        List<Account> adminAccounts = accountRepository.findByRole_Code("ADMIN");
        if (adminAccounts.isEmpty()) {
            Role adminRole = roleRepository.findRoleByCode("ADMIN").orElseThrow(() -> new NotFoundException("Could not found Admin Role"));
            Account adminAccount = Account.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin@123"))
                    .role(adminRole)
                    .status(EAccountStatus.ACTIVATE)
                    .build();
            accountRepository.save(adminAccount);
        }
    }

}
