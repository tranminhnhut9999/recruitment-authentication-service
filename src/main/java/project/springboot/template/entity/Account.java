package project.springboot.template.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboot.template.config.constants.EAccountStatus;
import project.springboot.template.config.constants.EEduLevel;
import project.springboot.template.config.constants.EGender;

import javax.persistence.*;

@Data
@Entity
@Table(name = "_account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Enumerated(EnumType.STRING)
    private EAccountStatus status = EAccountStatus.ACTIVATE;
    private String compPhone;
    private String perPhone;
    private String workingPlace;
    private String perAddress1;
    private String perAddress2;
    private String citizenID;
    private String emergencyContactName;
    private String emergencyPhoneNumber;
    private String avatarImg;
    @Enumerated(EnumType.STRING)
    private EEduLevel eduLevel;
    @Enumerated(EnumType.STRING)
    private EGender gender;
}
