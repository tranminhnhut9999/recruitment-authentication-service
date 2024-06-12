package project.springboot.template.service.impl;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import project.springboot.template.entity.common.ApiException;
import project.springboot.template.util.MessageUtil;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static project.springboot.template.util.common.Constants.ErrorMessage.CODE_ALREADY_EXISTED;
import static project.springboot.template.util.common.Constants.ErrorMessage.ROLE_NOT_FOUND_BY_ID;

@MockitoSettings(strictness = Strictness.LENIENT)
public class RoleServiceImplTest {


    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private Permission permission;

    Long idRole = 1L;
    String username = "test";
    String code = "test";
    String description = "The characteristics of someone or something";
    Long idPermission = 1L;

    Role role;
    RoleDto roleDto;
    RolePermissionsDto rolePermissionsDto;


    @BeforeEach
    public void initData() {

        role = new Role();
        role.setId(idRole);
        role.setCode(code);
        role.setName(username);
        role.setDescription(description);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        roleDto = new RoleDto();
        roleDto.setId(idRole);
        roleDto.setCode("code");
        roleDto.setName("name");
        roleDto.setDescription("The characteristics of someone or something");
        when(messageUtil.getLocalMessage(CODE_ALREADY_EXISTED)).thenReturn("Code already existed");
        when(messageUtil.getLocalMessage(ROLE_NOT_FOUND_BY_ID)).thenReturn("Role not found by ID");
        when(roleRepository.findById(roleDto.getId())).thenReturn(Optional.of(role));
    }

    @Test
    void shouldUpdateRoleSuccess() {
        RoleDto response = roleService.updateRole(1L, roleDto);
        Assertions.assertThat(response).isEqualToComparingFieldByField(roleDto);
    }

    @Test
    void shouldUpdateFailByExistCode() {
        RoleDto oldRole = new RoleDto();
        oldRole.setId(1L);
        oldRole.setCode("code");
        oldRole.setName("name");
        oldRole.setDescription("The characteristics of someone or something");
        when(roleRepository.existsByCode(roleDto.getCode()) && !oldRole.getCode().equals(roleDto.getCode())).thenReturn(true);
        ApiException apiException = assertThrows(ApiException.class, () -> roleService.updateRole(1L, roleDto));
        String message = apiException.getMessage();
        HttpStatus status = apiException.getStatus();
        assertEquals("Code already existed", message);
        assertEquals(HttpStatus.BAD_REQUEST, status);
    }


    @Test
    void updateRoleFailByNotFoundId() {
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findById(roleDto.getId())).thenReturn(ofResult);
        Long updatedID = 123L;
        ApiException apiException = assertThrows(ApiException.class, () -> roleService.updateRole(updatedID, roleDto));
        String message = apiException.getMessage();
        HttpStatus status = apiException.getStatus();
        assertEquals("Role not found by ID" + updatedID, message);
        assertEquals(HttpStatus.NOT_FOUND, status);
    }

//    @Test
//    void shouldSearchRoleSuccess(){
//        IRoleResponseTuple iRoleResponseTuple = mock(IRoleResponseTuple.class);
//        when(iRoleResponseTuple.getId()).thenReturn(123L);
//        when(iRoleResponseTuple.getCode()).thenReturn("Code");
//        when(iRoleResponseTuple.getDescription()).thenReturn("The characteristics of someone or something");
//        when(iRoleResponseTuple.getName()).thenReturn("Name");
//
//        ArrayList<IRoleResponseTuple> iRoleResponseTupleList = new ArrayList<>();
//        iRoleResponseTupleList.add(iRoleResponseTuple);
//        PageImpl<Object[]> pageImpl = new PageImpl<>(iRoleResponseTupleList);
//
//        when(roleRepository.findAllPageable((String) any(), (org.springframework.data.domain.Pageable) any()))
//                .thenReturn(pageImpl);
//
//        ApiPage<GetRoleResponse> actualSearchRoleResult = roleService.searchRole("query", null);
//        assertEquals(0, actualSearchRoleResult.getCurrentPage().intValue());
//        assertEquals(1, actualSearchRoleResult.getTotalPages().intValue());
//        assertEquals(1L, actualSearchRoleResult.getTotalItems().longValue());
//        assertEquals(1, actualSearchRoleResult.getPageSize().intValue());
//        assertEquals(1, actualSearchRoleResult.getPageItemSize().intValue());
//        assertTrue(actualSearchRoleResult.getLast());
//        List<GetRoleResponse> items = actualSearchRoleResult.getItems();
//        assertEquals(1, items.size());
//        GetRoleResponse getResult = items.get(0);
//        assertEquals(123L, getResult.getId());
//        assertEquals("Name" , getResult.getName());
//        assertEquals("Code" , getResult.getCode());
//        assertEquals("The characteristics of someone or something", getResult.getDescription());
//
//    }

    @Test
    void testDeleteRole() {

        Role role = new Role();
        role.setAccounts(new ArrayList<>());
        role.setCode("Code");
        role.setDescription("The characteristics of someone or something");
        role.setId(123L);
        role.setName("Name");
        role.setPermissions(new ArrayList<>());
        Optional<Role> ofResult = Optional.of(role);

        when(this.roleRepository.save((Role) any())).thenReturn(role);
        doNothing().when(this.roleRepository).deleteById((Long) any());
        when(this.roleRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals(123L, roleService.deleteRole(123L).longValue());
        verify(this.roleRepository).save((Role) any());
        verify(this.roleRepository).findById((Long) any());
        verify(this.roleRepository).deleteById((Long) any());
    }


}
