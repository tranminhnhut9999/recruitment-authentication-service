package project.springboot.template.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.grooo.vietdang.entity.tuple.IStaffResponseTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StaffRepositoryTest {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    Staff staff;
    Department department;
    Store store;
    Role role;
    Account account;

    @BeforeEach
    public void setUp() {
        System.out.println("---------------SET UP--------------------");
        if (staff == null) {
            department = new Department();
            department.setId(1L);
            departmentRepository.save(department);

            store = new Store();
            store.setId(1L);
            storeRepository.save(store);

            role = new Role();
            role.setId(1L);
            role.setName("ADMIN");
            role.setDescription("All");
            roleRepository.save(role);

            account = new Account();
            account.setId(1L);
            account.setActive(true);
            account.setType(EAccountType.STAFF);
            account.setUsername("thanhhuy");
            account.setPassword("123456");
            account.setStatus(EAccountStatus.ALIVE);
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            account.setRoles(roles);
            accountRepository.save(account);

            staff = new Staff();
            staff.setId(123L);
            staff.setName("ThanhHuy2");
            staff.setDepartment(department);
            staff.setStore(store);
            staff.setAccount(account);
            staffRepository.save(staff);
        }
    }

    @Test
    public void shouldFoundStaff() {
        Page page = staffRepository.findAllPageable(Pageable.unpaged());
        List<IStaffResponseTuple> staffResponseTuples = page.getContent();
        List<StaffDto> staffList = staffResponseTuples.stream().map(result -> {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(result.getStaffId());
            staffDto.setName(result.getName());
            staffDto.setDepartmentId(result.getDepartmentId());
            staffDto.setStoreId(result.getStoreId());
            return staffDto;
        }).collect(Collectors.toList());

        assertEquals(1, staffList.size());
    }

    @Test
    public void shouldFoundStaffByKeyword() {
        String keyword = "thanhhuy";
        Page page = staffRepository.findAllPageable(keyword, ERoleStatus.ALL, Pageable.unpaged());
        List<IStaffResponseTuple> staffResponseTuples = page.getContent();
        List<StaffDto> staffList = staffResponseTuples.stream().map(result -> {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(result.getStaffId());
            staffDto.setName(result.getName());
            staffDto.setDepartmentId(result.getDepartmentId());
            staffDto.setStoreId(result.getStoreId());
            return staffDto;
        }).collect(Collectors.toList());

        Assertions.assertThat(staffList.size()).isGreaterThan(0);
    }

    @Test
    public void shouldNotFoundStaffByKeyword() {
        String keyword = "nothing";
        Page page = staffRepository.findAllPageable(keyword, ERoleStatus.ALL, Pageable.unpaged());
        List<IStaffResponseTuple> staffResponseTuples = page.getContent();
        List<StaffDto> staffList = staffResponseTuples.stream().map(result -> {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(result.getStaffId());
            staffDto.setName(result.getName());
            staffDto.setDepartmentId(result.getDepartmentId());
            staffDto.setStoreId(result.getStoreId());
            return staffDto;
        }).collect(Collectors.toList());

        Assertions.assertThat(staffList.size()).isLessThan(1);
    }

    @Test
    public void shouldFoundStaffByRoleStatus() {
        String keyword = "";
        Page page = staffRepository.findAllPageable(keyword, ERoleStatus.HAD, Pageable.unpaged());
        List<IStaffResponseTuple> staffResponseTuples = page.getContent();
        List<StaffDto> staffList = staffResponseTuples.stream().map(result -> {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(result.getStaffId());
            staffDto.setName(result.getName());
            staffDto.setDepartmentId(result.getDepartmentId());
            staffDto.setStoreId(result.getStoreId());
            return staffDto;
        }).collect(Collectors.toList());

        Assertions.assertThat(staffList.size()).isGreaterThan(0);
    }

    @Test
    public void shouldNotFoundStaffByRoleStatus() {
        String keyword = "";
//        Pageable pageable = PageRequest.of(0, 20);
        Page page = staffRepository.findAllPageable(keyword, ERoleStatus.NA, Pageable.unpaged());
        List<IStaffResponseTuple> staffResponseTuples = page.getContent();
        List<StaffDto> staffList = staffResponseTuples.stream().map(result -> {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(result.getStaffId());
            staffDto.setName(result.getName());
            staffDto.setDepartmentId(result.getDepartmentId());
            staffDto.setStoreId(result.getStoreId());
            return staffDto;
        }).collect(Collectors.toList());

        Assertions.assertThat(staffList.size()).isLessThan(1);
    }


}

