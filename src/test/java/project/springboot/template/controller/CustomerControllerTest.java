package project.springboot.template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.springboot.template.entity.common.ApiPage;
import vn.grooo.vietdang.service.impl.CustomerServiceImpl;
import project.springboot.template.util.MapperUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    CustomerController customerController;

    private final ObjectMapper objectMapper = MapperUtil.getInstance().getMapper();
    private ApiPage<GetCustomerResponse> getCustomerResponseApiPage;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        getCustomerResponseApiPage = new ApiPage<>();
        List<GetCustomerResponse> getCustomerResponseList = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            GetCustomerResponse getCustomerResponse = new GetCustomerResponse();
            getCustomerResponse.setId(new Long(index));
            getCustomerResponse.setName("thanhhuy123");
            getCustomerResponseList.add(getCustomerResponse);
        }
        getCustomerResponseApiPage.setItems(getCustomerResponseList);
        Mockito.when(customerService.searchAllCustomer(any(String.class), any(ECustomerType.class), any(Boolean.class), any())).thenReturn(getCustomerResponseApiPage);
    }

    @Test
    public void shouldFoundCustomer() throws Exception {
        String keyword = "thanhhuy";
        MockHttpServletRequestBuilder request = get("/api/customer/search")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .param("q", keyword)
                .param("type", "ALL");

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}
