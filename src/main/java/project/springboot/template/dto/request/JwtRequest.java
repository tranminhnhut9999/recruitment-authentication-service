package project.springboot.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Tài khoản")
    private String username;

    @Schema(description = "Mật khẩu")
    private String password;
}
