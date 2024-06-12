package project.springboot.template.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboot.template.config.constants.EAccountStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String roleCode;
    private EAccountStatus status = EAccountStatus.ACTIVATE;
    private String workingPlace;
}
