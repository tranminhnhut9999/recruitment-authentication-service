package project.springboot.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.springboot.template.config.constants.EAccountStatus;
import project.springboot.template.config.constants.EEduLevel;
import project.springboot.template.config.constants.EGender;
import project.springboot.template.dto.request.ChangePasswordRequest;
import project.springboot.template.dto.request.RegisterRequest;
import project.springboot.template.dto.response.ProfileResponse;
import project.springboot.template.dto.response.RoleResponse;
import project.springboot.template.entity.Account;
import project.springboot.template.entity.Role;
import project.springboot.template.entity.common.ApiException;
import project.springboot.template.repository.AccountRepository;
import project.springboot.template.repository.RoleRepository;
import project.springboot.template.util.ObjectUtil;
import project.springboot.template.util.SecurityUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public void changePassword(ChangePasswordRequest request) {

        Account account = SecurityUtil.getCurrentUserAccountLogin()
                .orElseThrow(() -> ApiException.create(HttpStatus.FORBIDDEN).withMessage("Access Denied"));
        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        accountRepository.save(account);
    }

    public boolean createNewUser(RegisterRequest request) {
        try {
            boolean existedAccount = accountRepository.findByEmail(request.getEmail()).isPresent();
            if (existedAccount) {
                throw ApiException.create(HttpStatus.PRECONDITION_FAILED).withMessage("email has existed, please choose another one!");
            }
            Account.AccountBuilder accountBuilder = Account.builder()
                    .email(request.getEmail())
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .password(passwordEncoder.encode(request.getPassword().trim()))
                    .status(request.getStatus())
                    .workingPlace(request.getWorkingPlace());

            String roleCode = request.getRoleCode();
            if (roleCode != null && !roleCode.isEmpty()) {
                Role role = roleRepository.findRoleByCode(roleCode)
                        .orElseThrow(() -> ApiException.create(HttpStatus.PRECONDITION_FAILED).withMessage("not found role:" + roleCode));
                accountBuilder.role(role);
            }
            Account newAccount = accountBuilder.build();
            this.accountRepository.save(newAccount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ProfileResponse getUserProfileByUser() {
        Account account = SecurityUtil.getCurrentUserAccountLogin().orElseThrow(() -> ApiException.create(HttpStatus.FORBIDDEN));
        ProfileResponse profileResponse = ObjectUtil.copyProperties(account, new ProfileResponse(), ProfileResponse.class, true);
        if (account.getEduLevel() != null) {
            EEduLevel eduLevel = account.getEduLevel();
            profileResponse.setEduLevelCode(eduLevel.name());
            profileResponse.setEduLevelDescription(eduLevel.getDescription());

        }
        if (account.getGender() != null) {
            EGender gender = account.getGender();
            profileResponse.setGenderCode(gender.name());
            profileResponse.setGenderDescription(gender.getDescription());
        }
        RoleResponse roleResponse = ObjectUtil.copyProperties(account.getRole(), new RoleResponse(), RoleResponse.class, true);
        profileResponse.setRole(roleResponse);
        return profileResponse;
    }

    public boolean changeAccountStatus(String email, EAccountStatus status){
        Account changeAccount = this.accountRepository
                .findByEmail(email).orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Account not found with email:" + email));
        changeAccount.setStatus(status);
        this.accountRepository.save(changeAccount);
        return true;
    }

    public List<ProfileResponse> getAccountByRole(String roleCode){
        List<Account> accountsWithRole = this.accountRepository.findByRole_Code(roleCode);
        return accountsWithRole.stream()
                .map(a -> ObjectUtil.copyProperties(a, new ProfileResponse(), ProfileResponse.class, true)).collect(Collectors.toList());
    }
}
