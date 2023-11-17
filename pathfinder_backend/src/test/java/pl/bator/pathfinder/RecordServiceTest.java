package pl.bator.pathfinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bator.pathfinder.infrastructure.common.entity.Record;
import pl.bator.pathfinder.infrastructure.common.repository.RecordRepository;
import pl.bator.pathfinder.infrastructure.record.RecordService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTest {

    @InjectMocks
    private RecordService recordService;
    @Mock
    private RecordRepository recordRepository;
    @Test
    public void testGetRecords() {
        //given
        Record record1 = new Record(1L, "Tytuł 1");
        Record record2 = new Record(2L, "Tytuł 2");
        List<Record> records = Arrays.asList(record1, record2);
        //when
        when(recordRepository.findAll()).thenReturn(records);
        //then
        List<Record> result = recordService.getRecords();
        assertEquals(records, result);
    }
}
