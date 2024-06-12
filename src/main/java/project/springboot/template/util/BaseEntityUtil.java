package project.springboot.template.util;

import project.springboot.template.entity.common.BaseEntity;

import java.time.Instant;

public class BaseEntityUtil {

    private BaseEntityUtil() {
    }

    public static <T extends BaseEntity> void updateEntity(T entity) {
        if (entity == null) {
            return;
        }
        String currentUser = SecurityUtil.getCurrentUsernameLogin().orElse("SYSTEM");
        entity.setLastModified(Instant.now());
        entity.setLastModifiedBy(currentUser);
    }
}
