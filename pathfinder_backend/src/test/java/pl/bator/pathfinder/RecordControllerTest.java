package pl.bator.pathfinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.bator.pathfinder.infrastructure.common.config.JwtAuthorizationFilter;
import pl.bator.pathfinder.infrastructure.common.config.JwtUtil;
import pl.bator.pathfinder.infrastructure.common.entity.Record;
import pl.bator.pathfinder.infrastructure.record.RecordController;
import pl.bator.pathfinder.infrastructure.record.RecordService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RecordController.class)
@WithMockUser(username = "marcinbator.ofc@gmail.com")
public class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private RecordService recordService;

    public static List<Record> createRecords() {
        Record record1 = new Record(1L, "Tytuł 1");
        Record record2 = new Record(2L, "Tytuł 2");
        return Arrays.asList(record1, record2);
    }

    @Test
    public void testGetRecords() throws Exception {
        //given
        List<Record> records = createRecords();
        String expectedJson = objectMapper.writeValueAsString(records);
        //when
        when(recordService.getRecords()).thenReturn(records);
        //then
        mockMvc.perform(get("/api/record").header("Authorization", "Bearer " +
                jwtUtil.generateToken(new JwtUtil.Input("marcinbator.ofc@gmail.com"))))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}