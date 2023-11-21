package pl.bator.pathfinder.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bator.pathfinder.entity.Record;
import pl.bator.pathfinder.entity.repository.RecordRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public List<Record> getRecords() {
        return recordRepository.findAll();
    }
}
