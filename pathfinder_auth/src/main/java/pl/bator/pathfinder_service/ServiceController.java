package pl.bator.pathfinder_service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@AllArgsConstructor
@RequestMapping("/api/get")
public class ServiceController {
    private WebClient webClient;

    @GetMapping("/{name}")
    public ResponseEntity<String> isInDb(@PathVariable String name) {
        return ResponseEntity.ok(webClient.get()
                .uri("http://localhost:8080/api/record")
                .retrieve()
                .bodyToMono(String.class)
                .block());
    }
}
