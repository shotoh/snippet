package to.us.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import to.us.snippet.auth.AuthController;
import to.us.snippet.users.UserCreateDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
	private final MockMvc mockMvc;
	private final AuthController controller;
	private final ObjectMapper mapper;

	@Autowired
	public AuthControllerTests(MockMvc mockMvc, AuthController controller) {
		this.mockMvc = mockMvc;
		this.controller = controller;
		this.mapper = new ObjectMapper();
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void registerInvalidUsername() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("");
		registerDTO.setEmail("mock@gmail.com");
		registerDTO.setPassword("mock_pass");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.username").value("size must be between 1 and 31"));
	}

	@Test
	void registerNullUsername() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername(null);
		registerDTO.setEmail("mock@gmail.com");
		registerDTO.setPassword("mock_pass");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.username").value("must not be null"));
	}

	@Test
	void registerInvalidEmail() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("mockUser1");
		registerDTO.setEmail("invalidEmail");
		registerDTO.setPassword("mock_pass");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.email").value("must be a well-formed email address"));
	}

	@Test
	void registerNullEmail() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("mockUser1");
		registerDTO.setEmail(null);
		registerDTO.setPassword("mock_pass");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.email").value("must not be null"));
	}
}
