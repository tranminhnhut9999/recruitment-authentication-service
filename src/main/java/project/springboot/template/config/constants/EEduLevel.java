package project.springboot.template.config.constants;

import lombok.Getter;

@Getter
public enum EEduLevel {
    PRIMARY("Giáo dục tiểu học"),
    SECONDARY("Giáo dục trung học"),
    UNDERGRADUATE("Giáo dục đại học"),
    POSTGRADUATE("Giáo dục sau đại học"),
    DOCTORATE("Giáo dục tiến sĩ");

    private final String description;

    EEduLevel(String description) {
        this.description = description;
    }
}
