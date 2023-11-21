package pl.bator.pathfinder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest //load all context
@ActiveProfiles("dev") //load only dev profile
class PathfinderBackendApplicationTests {
    @Test
    void contextLoads() {
    }

}
