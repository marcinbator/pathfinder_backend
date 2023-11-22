package pl.bator.pathfinder.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bator.pathfinder.entity.Record;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    @Operation(summary = "Get all records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get records"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Record>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }
}
