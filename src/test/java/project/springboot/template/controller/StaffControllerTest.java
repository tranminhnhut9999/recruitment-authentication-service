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
import vn.grooo.vietdang.service.StaffService;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StaffControllerTest {
    @Mock
    private PermissionService permissionService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private StaffController staffController;

    @Mock
    private StaffService staffService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(this.staffController)
                .build();
    }

    @Test
    void testUpdateStaff() throws Exception {
//        StaffDto staffDto = new StaffDto();
//        staffDto.setDepartmentId(123L);
//        staffDto.setId(123L);
//        staffDto.setName("Name");
//        staffDto.setStoreId(123L);
//        when(this.staffService.updateStaff((Long) any(), (StaffDto) any())).thenReturn(staffDto);

        StaffDto updatedStaff = new StaffDto();
        updatedStaff.setDepartmentId(123L);
        updatedStaff.setId(123L);
        updatedStaff.setName("Name");
        updatedStaff.setStoreId(123L);
        String content = (new ObjectMapper()).writeValueAsString(updatedStaff);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/staff/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(updatedStaff.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(updatedStaff.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.departmentId").value(updatedStaff.getDepartmentId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.storeId").value(updatedStaff.getStoreId()));

    }


    @Test
    void shouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/staff/2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(("null"));

        mockMvc.perform(request)
//                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}

