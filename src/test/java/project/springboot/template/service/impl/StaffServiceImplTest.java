package project.springboot.template.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.springboot.template.entity.common.ApiException;
import project.springboot.template.util.MessageUtil;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class StaffServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private StaffServiceImpl staffService;

    private Account account;

    private Store store;

    private Department department;

    private Staff staff;

    String passwordEncoded;
    String username = "test";
    Long id = 1L;
    EAccountType accountType = EAccountType.STAFF;
    EAccountStatus accountStatus = EAccountStatus.ALIVE;
    boolean isActive = true;
    String staffName = "test";
    Long staffId = 1L;
    AccountStaffDto accountStaffDto;

    StaffDto staffDto;

    @BeforeEach
    public void initData() {

        accountStaffDto = new AccountStaffDto();
        accountStaffDto.setStoreId(1L);
        accountStaffDto.setDepartmentId(1L);
        accountStaffDto.setPassword("test");
        accountStaffDto.setIsActive(true);
        accountStaffDto.setName(staffName);
        accountStaffDto.setType(accountType);
        accountStaffDto.setStatus(accountStatus);
        accountStaffDto.setUsername(username);

        when(messageUtil.getLocalMessage(USERNAME_ALREADY_EXISTED)).thenReturn("Username already existed");

        when(passwordEncoder.encode(any())).thenReturn("password_encoded");

        passwordEncoded = passwordEncoder.encode("test");

        account = new Account();
        department = new Department();
        store = new Store();
        staff = new Staff();

        when(accountRepository.countByUsername(username)).thenReturn(0L);

        account.setId(id);
        account.setType(accountType);
        account.setStatus(accountStatus);
        account.setUsername(username);
        account.setPassword(passwordEncoded);
        account.setIsActive(isActive);

        when((accountRepository.save(any(Account.class)))).thenReturn(account);

        department.setId(1L);
        department.setName("Department1");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        store.setId(1L);
        store.setName("Store1");
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        staff.setId(staffId);
        staff.setName(staffName);
        staff.setAccount(account);
        staff.setAccountStatus(accountStatus);
        staff.setDepartment(department);
        staff.setStore(store);

        when(staffRepository.save(any(Staff.class))).thenReturn(staff);

        when(roleRepository.findAllById(any())).thenReturn(new ArrayList<>());

        staffDto = new StaffDto();
        staffDto.setId(staffId);
        staffDto.setName(staffName);
        staffDto.setStoreId(store.getId());
        staffDto.setDepartmentId(department.getId());

        when(staffRepository.findById(id)).thenReturn(Optional.of(staff));

        when(departmentRepository.findById(staffDto.getDepartmentId())).thenReturn(Optional.of(department));

        when(storeRepository.findById(staffDto.getStoreId())).thenReturn(Optional.of(store));

    }

    @Test
    public void shouldCreateNewStaffAccountSuccess() {
        AccountStaffDto response = staffService.createNewStaffAccount(accountStaffDto);

        accountStaffDto.setStaffId(staff.getId());
        accountStaffDto.setAccountId(account.getId());

        assertEquals(response, accountStaffDto);

    }

    @Test
    public void shouldCreateNewStaffThrowUsernameExistedException() {
        accountStaffDto.setUsername("test_exist");

        when(accountRepository.countByUsername("test_exist")).thenReturn(1L);

        ApiException apiException = assertThrows(ApiException.class, () -> staffService.createNewStaffAccount(accountStaffDto));

        String message = apiException.getMessage();
        HttpStatus status = apiException.getStatus();
        assertEquals("Username already existed", message);
        assertEquals(HttpStatus.BAD_REQUEST, status);
    }

    @Test
    void shouldUpdateStaffSuccess() {
        StaffDto response = staffService.updateStaff(staffId, staffDto);
        Assertions.assertThat(response).isEqualToComparingFieldByField(staffDto);

    }

    @Test
    void shouldUpdateFailByNonExistDepartment() {
        when(departmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        when(messageUtil.getLocalMessage(DEPARTMENT_NOT_FOUND_BY_ID)).thenReturn("Department not found:");

        ApiException exception = assertThrows(ApiException.class, () -> staffService.updateStaff(staffId, staffDto));
        HttpStatus httpStatus = exception.getStatus();
        String message = exception.getMessage();
        assertEquals("Department not found:" + department.getId(), message);
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
    }

    @Test
    void shouldUpdateFailByNonExistStore() {
        when(storeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        when(messageUtil.getLocalMessage(STORE_NOT_FOUND_BY_ID)).thenReturn("Store not found:");

        ApiException exception = assertThrows(ApiException.class, () -> staffService.updateStaff(staffId, staffDto));

        HttpStatus httpStatus = exception.getStatus();
        String message = exception.getMessage();

        assertEquals("Store not found:" + department.getId(), message);
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
    }

//    @Test
//    void shouldSearchStaffSuccess (){
//        IStaffResponseTuple iRoleResponseTuple = mock(IStaffResponseTuple.class);
//        when(iRoleResponseTuple.getStaffId()).thenReturn(1L);
//        ArrayList<IStaffResponseTuple> staffResponseTupleArrayList = new ArrayList<>();
//        staffResponseTupleArrayList.add(iRoleResponseTuple);
//        PageImpl<IStaffResponseTuple> pageImpl = new PageImpl<>(staffResponseTupleArrayList);
//
//        when(staffRepository.findAllPageable((String) any(), any(), any())).thenReturn(pageImpl);
//
//        ApiPage<GetStaffResponse> searchStaff = staffService.searchStaff("query", ERoleStatus.ALL , Pageable.unpaged()) ;
//        List<GetStaffResponse> items = searchStaff.getItems();
//        assertEquals(1, items.size());
//        GetStaffResponse getResult = items.get(0);
//        assertEquals(1L, getResult.getStaffId());
//
//
//    }
}
