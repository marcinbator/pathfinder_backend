package pl.bator.pathfinder.infrastructure.record;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bator.pathfinder.infrastructure.common.entity.Record;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<Record>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }
}
