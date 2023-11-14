package pl.bator.pathfinder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.bator.pathfinder.infrastructure.common.entity.Record;
import pl.bator.pathfinder.infrastructure.record.RecordController;
import pl.bator.pathfinder.infrastructure.record.RecordService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecordController.class)
@ActiveProfiles("dev")
public class RecordTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RecordService recordService;
    @Test
    public void testGetRecordsEndpoint() throws Exception {
        List<Record> records= new ArrayList<>();
        when(recordService.getRecords()).thenReturn(records);
        mockMvc.perform(get("/api/record"))
                .andExpect(status().isOk());
    }
}
