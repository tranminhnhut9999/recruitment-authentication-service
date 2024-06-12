package project.springboot.template.dto.response;

import lombok.Data;
import project.springboot.template.config.constants.EAccountStatus;
import project.springboot.template.config.constants.EEduLevel;
import project.springboot.template.config.constants.EGender;

import javax.persistence.*;

@Data
public class ProfileResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleResponse role;
    private String status = EAccountStatus.ACTIVATE.name();
    private String compPhone;
    private String perPhone;
    private String workingPlace;
    private String perAddress1;
    private String perAddress2;
    private String citizenID;
    private String emergencyContactName;
    private String emergencyPhoneNumber;
    private String avatarImg;
    private String eduLevelCode;
    private String eduLevelDescription;
    private String genderCode;
    private String genderDescription;
}
