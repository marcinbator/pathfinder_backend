package pl.bator.pathfinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.bator.pathfinder.config.JwtUtil;
import pl.bator.pathfinder.entity.Record;
import pl.bator.pathfinder.entity.repository.RecordRepository;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@WithMockUser(username = "marcinbator.ofc@gmail.com")
@AutoConfigureMockMvc //for integration tests
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //for db integration tests
public class RecordIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RecordRepository recordRepository;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void shouldGetRecords() throws Exception {
        //given
        Record record1 = new Record();
        Record record2 = new Record();
        record1.setName("record1");
        record2.setName("record2");
        List<Record> records = Arrays.asList(record1, record2);
        //when
        recordRepository.deleteAll();
        recordRepository.saveAll(records);
        //then
        mockMvc.perform(get("/api/record").header("Authorization", "Bearer " +
                        jwtUtil.generateToken(new JwtUtil.Input("marcinbator.ofc@gmail.com", null))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }
}
