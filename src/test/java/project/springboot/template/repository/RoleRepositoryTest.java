package project.springboot.template.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.grooo.vietdang.entity.*;
import vn.grooo.vietdang.entity.tuple.IRoleResponseTuple;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockitoSettings(strictness = Strictness.LENIENT)

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private ActionRepository actionRepository;

    Role role;
    Staff staff;
    Department department;
    Store store;

    Account account;
    Permission permission;
    Function function;
    Action action;

    @BeforeEach
    public void setUp() {

        department = new Department();
        department.setId(1L);
        departmentRepository.save(department);

        store = new Store();
        store.setId(1L);
        storeRepository.save(store);

        role = new Role();
        role.setId(1L);
        role.setCode("code role");
        role.setName("Role Name");
        role.setDescription("this is description");
        roleRepository.save(role);

        account = new Account();
        account.setId(1L);
        account.setIsActive(true);

        account.setType(EAccountType.STAFF);

        account.setUsername("accountName");
        account.setPassword("123456");
        account.setStatus(EAccountStatus.ALIVE);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        account.setRoles(roles);
        accountRepository.save(account);

        staff = new Staff();
        staff.setId(1L);
        staff.setName("staffName");
        staff.setDepartment(department);
        staff.setStore(store);
        staff.setAccount(account);
        staffRepository.save(staff);


    }

    @Test
    public void testSearchRole() {

        Page page = roleRepository.findAllPageable("hai dang", Pageable.unpaged());

        List<IRoleResponseTuple> roleResponseTuple = page.getContent();
        List<GetRoleResponse> roleList = roleResponseTuple.stream().map(result -> {
            GetRoleResponse getRoleResponse = new GetRoleResponse();
            getRoleResponse.setCode(result.getCode());
            assertEquals("code role", getRoleResponse.getCode());
            return getRoleResponse;
        }).collect(Collectors.toList());
        Assertions.assertThat(roleList.size()).isGreaterThan(0);

    }

/*    @Test
    public void testGetAccountByRole() {

        Page page = roleRepository.findAllPageable(1L, Pageable.unpaged());
        List<IStaffResponseTuple> staffResponseTuple = page.getContent();
        List<GetStaffResponse> staffList = staffResponseTuple.stream().map(result -> {
            GetStaffResponse getStaffResponse = new GetStaffResponse();

            getStaffResponse.setStaffId(result.getStaffId());
            assertEquals(1, getStaffResponse.getStaffId());

            getStaffResponse.setName(result.getName());
            assertEquals("staffName", getStaffResponse.getName());

            getStaffResponse.setDepartmentId(result.getDepartmentId());
            assertEquals(1, getStaffResponse.getDepartmentId());

            getStaffResponse.setAccountId(result.getAccountId());
            assertEquals(1, getStaffResponse.getAccountId());
            getStaffResponse.setIsActive(result.getIsActive());
            getStaffResponse.setUsername(result.getUsername());
            assertEquals("accountName", getStaffResponse.getUsername());

            getStaffResponse.setStoreId(result.getStoreId());
            assertEquals(1, getStaffResponse.getStoreId());

            return getStaffResponse;
        }).collect(Collectors.toList());
        Assertions.assertThat(staffList.size()).isGreaterThan(0);
    }*/


}
