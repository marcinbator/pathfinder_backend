package pl.bator.pathfinder.infrastructure.record;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bator.pathfinder.infrastructure.common.entity.Record;
import pl.bator.pathfinder.infrastructure.common.repository.RecordRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public List<Record> getRecords() {
        return recordRepository.findAll();
    }
}
