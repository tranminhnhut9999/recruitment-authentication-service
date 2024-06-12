package project.springboot.template.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Access token")
    private String token;

    private String email;

    private String name;

    private List<String> roleCodes = new ArrayList<>();

    private Long accountId;

    private String avatarImageUrl;
}
