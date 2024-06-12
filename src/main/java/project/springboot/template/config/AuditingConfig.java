package project.springboot.template.config;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.springboot.template.util.SecurityUtil;
import project.springboot.template.util.common.Constants;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    private final EntityManagerFactory entityManagerFactory;

    public AuditingConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(SecurityUtil.getCurrentUsernameLogin().orElse(Constants.ANONYMOUS_USER));
    }

    // envers
    @Bean
    AuditReader auditReader() {
        return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
    }

}