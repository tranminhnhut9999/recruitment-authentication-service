package project.springboot.template.dto.request;

import lombok.Data;
import project.springboot.template.config.constants.EAccountStatus;

@Data
public class ChangeAccountStatusRequest {
    private String email;
    private EAccountStatus status;
}
