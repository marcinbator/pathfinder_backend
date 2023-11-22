package pl.bator.pathfinder_service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.bator.pathfinder_service.infrastructure.AuthController;
import pl.bator.pathfinder_service.infrastructure.AuthService;

import static org.hamcrest.Matchers.isOneOf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService; //has to be mocked

    @Test
    @WithMockUser(username = "marcinbator.ofc@gmail.com")
    public void shouldReturnUser() throws Exception {
        //given
        var user = new DefaultOidcUser(null, OidcIdToken //oidc user
                .withTokenValue("token")
                .claim("email", "marcinbator.ofc@gmail.com")
                .build(), "email");
        //then
        mockMvc.perform(get("/api/get/me").with(oidcLogin().oidcUser(user))) //with oidc login with user
                .andExpect(status().isOk())
                .andExpect(content().string(user.getEmail()));
    }

    @Test
    public void shouldReturn401Or302() throws Exception {
        mockMvc.perform(get("/api/get/me"))
                .andExpect(status().is(isOneOf(HttpStatus.UNAUTHORIZED.value(), HttpStatus.FOUND.value())));
    }


}
