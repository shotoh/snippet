package to.us.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import to.us.snippet.auth.AuthDTO;
import to.us.snippet.auth.AuthService;
import to.us.snippet.auth.PasswordDTO;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.users.UserDTO;
import to.us.snippet.users.UserRepository;
import to.us.snippet.users.UserService;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper mapper;

	private UserDTO mockUser;
	private String mockToken;

	@Autowired
	public AuthControllerTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
		this.mapper = new ObjectMapper();
	}

	@Test
	void contextLoads() {
		//
	}

	@BeforeEach
	void setup(@Autowired UserService userService, @Autowired AuthService auth,
	           @Value("${MOCK_PASSWORD:}") String mockPassword) {
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setUsername("mock");
		userCreateDTO.setEmail("mock@gmail.com");
		userCreateDTO.setPassword(mockPassword);
		this.mockUser = userService.createUser(userCreateDTO);

		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword(mockPassword);
		this.mockToken = "Bearer " + auth.login(authDTO).getToken();
	}

	@AfterEach
	void clean(@Autowired UserRepository repository) {
		repository.deleteById(mockUser.getId());
	}

	@Test
	void registerInvalidUsername() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("");
		registerDTO.setEmail("mock1@gmail.com");
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
		registerDTO.setEmail("mock1@gmail.com");
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

	@Test
	void registerNullPassword() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("mockUser1");
		registerDTO.setEmail("mock1@gmail.com");
		registerDTO.setPassword(null);
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.password").value("must not be null"));
	}

	@Test
	void registerUserExists() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername(mockUser.getUsername());
		registerDTO.setEmail("mock1@gmail.com");
		registerDTO.setPassword("abc");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.username").value("User already exists with this username"));
	}

	@Test
	void registerEmailExists() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("mock1");
		registerDTO.setEmail(mockUser.getEmail());
		registerDTO.setPassword("abc");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.email").value("User already exists with this email"));
	}

	@Test
	void register() throws Exception {
		UserCreateDTO registerDTO = new UserCreateDTO();
		registerDTO.setUsername("mockRegister");
		registerDTO.setEmail("mockRegister@gmail.com");
		registerDTO.setPassword("abc");
		mockMvc.perform(post("/api/auth/register")
						.content(mapper.writeValueAsString(registerDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void loginWrongCredentials() throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword("abc");
		mockMvc.perform(post("/api/auth/login")
						.content(mapper.writeValueAsString(authDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void login(@Value("${MOCK_PASSWORD:}") String mockPassword) throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword(mockPassword);
		mockMvc.perform(post("/api/auth/login")
						.content(mapper.writeValueAsString(authDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data.token").exists());
	}

	@Test
	void changePasswordInvalidUser() throws Exception {
		PasswordDTO passwordDTO = new PasswordDTO();
		passwordDTO.setOldPassword("abc");
		passwordDTO.setNewPassword("def");
		mockMvc.perform(post("/api/auth/change-password")
						.content(mapper.writeValueAsString(passwordDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void changePasswordWrongCredentials() throws Exception {
		PasswordDTO passwordDTO = new PasswordDTO();
		passwordDTO.setOldPassword("abc");
		passwordDTO.setNewPassword("def");
		mockMvc.perform(post("/api/auth/change-password")
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(passwordDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void changePassword(@Value("${MOCK_PASSWORD:}") String mockPassword) throws Exception {
		PasswordDTO passwordDTO = new PasswordDTO();
		passwordDTO.setOldPassword(mockPassword);
		passwordDTO.setNewPassword(mockPassword + 1);
		mockMvc.perform(post("/api/auth/change-password")
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(passwordDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isEmpty());
	}
}
