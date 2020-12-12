import com.example.demo.DemoApplication;
import com.example.demo.dao.ProjectDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.representation.ProjectRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = DemoApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void signupAndThenLoginAndThenAddProjectAndThenGetProject()
            throws Exception {
        User u = new User("artyom", "password", "", "", "");
        mvc.perform(put("/api/v1/users/signup")
                .param("login", "artyom")
                .param("password", "password").content(asJsonString(u))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mvc.perform(get("/api/v1/users/login")
                .param("login", "artyom")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        ProjectRepresentation projectRepresentation = new ProjectRepresentation("test", "", "", new ArrayList<>());

        mvc.perform(put("/api/v1/users/add_project")
                .param("login", "artyom")
                .param("password", "password")
                .content(asJsonString(projectRepresentation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mvc.perform(get("/api/v1/users/get_user_project")
                .param("name", "test")
                .param("login", "artyom")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}