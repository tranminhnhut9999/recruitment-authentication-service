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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.grooo.vietdang.service.impl.StoreServiceImpl;
import project.springboot.template.util.MapperUtil;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class StoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StoreServiceImpl storeService;

    @InjectMocks
    StoreController storeController;

    private final ObjectMapper objectMapper = MapperUtil.getInstance().getMapper();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storeController)
                .build();
    }

    @Test
    public void shouldCreateNewStoreSuccess() throws Exception {

        StoreDto storeDto = new StoreDto();
        storeDto.setName("CUA HANG TEST");

        StoreDto savedStore = new StoreDto();
        savedStore.setName("CUA HANG TEST");
        savedStore.setId(1L);

        when(storeService.createNewStore(any())).thenReturn(savedStore);

        MockHttpServletRequestBuilder request = post("/api/store")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(storeDto));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(jsonPath("$.data.id").value(savedStore.getId()))
                .andExpect(jsonPath("$.data.name").value(savedStore.getName()));
    }

    @Test
    public void shouldCreateNewStoreReturnBadRequest() throws Exception {

        MockHttpServletRequestBuilder request = post("/api/store")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(("null"));

        mockMvc.perform(request)
//                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
