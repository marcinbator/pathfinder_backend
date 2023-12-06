package pl.bator.pathfinder_service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.bator.pathfinder_service.infrastructure.AuthController;
import pl.bator.pathfinder_service.infrastructure.AuthService;

import static org.hamcrest.Matchers.isOneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@ActiveProfiles("dev")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService; //has to be mocked

    @Test
    public void shouldReturn401Or302() throws Exception {
        mockMvc.perform(get("/api/get/me"))
                .andExpect(status().is(isOneOf(HttpStatus.UNAUTHORIZED.value(), HttpStatus.FOUND.value())));
    }
}
