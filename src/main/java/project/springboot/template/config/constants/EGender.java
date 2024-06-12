package project.springboot.template.config.constants;

import lombok.Getter;

@Getter
public enum EGender {
    MALE("Nam"),
    FEMALE("Nữ"),
    NON_BINARY("Phi nhị phân"),
    OTHER("Khác");

    private final String description;

    EGender(String description) {
        this.description = description;
    }
}
