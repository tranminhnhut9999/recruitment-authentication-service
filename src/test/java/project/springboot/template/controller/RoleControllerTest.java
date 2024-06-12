package project.springboot.template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.grooo.vietdang.service.PermissionService;
import vn.grooo.vietdang.service.RoleService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private PermissionService permissionService;

    @Mock
    private RoleService roleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .build();
    }

    @Test
    void testUpdateRole() throws Exception {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setCode("test");
        roleDto.setName("hai dang");
        roleDto.setDescription("test");
        when(this.roleService.updateRole((Long) any(), (RoleDto) any())).thenReturn(roleDto);

        RoleDto updatedRole = new RoleDto();
        updatedRole.setId(1L);
        updatedRole.setCode("test");
        updatedRole.setName("hai dang");
        updatedRole.setDescription("test");
        String content = (new ObjectMapper()).writeValueAsString(updatedRole);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/role/{roleId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(updatedRole.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.code").value(updatedRole.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(updatedRole.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description").value(updatedRole.getDescription()));
    }

    @Test
    void shouldUpdateRoleReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/role/2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(("null"));

        mockMvc.perform(request)
//                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
