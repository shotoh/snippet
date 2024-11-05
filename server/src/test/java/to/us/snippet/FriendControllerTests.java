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
import to.us.snippet.friends.FriendCreateDTO;
import to.us.snippet.friends.FriendService;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.users.UserDTO;
import to.us.snippet.users.UserRepository;
import to.us.snippet.users.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper mapper;

	private UserDTO mockUser;
	private String mockToken;
	private UserDTO mockUser2;
	private String mockToken2;

	@Autowired
	public FriendControllerTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
		this.mapper = new ObjectMapper();
	}

	@BeforeEach
	void setup(@Autowired UserService userService, @Autowired AuthService auth,
	           @Value("${MOCK_PASSWORD:}") String mockPassword) {
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setUsername("mock1");
		userCreateDTO.setEmail("mock1@gmail.com");
		userCreateDTO.setPassword(mockPassword);
		this.mockUser = userService.createUser(userCreateDTO);

		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword(mockPassword);
		this.mockToken = "Bearer " + auth.login(authDTO).getToken();

		UserCreateDTO userCreateDTO2 = new UserCreateDTO();
		userCreateDTO2.setUsername("mock2");
		userCreateDTO2.setEmail("mock2@gmail.com");
		userCreateDTO2.setPassword(mockPassword);
		this.mockUser2 = userService.createUser(userCreateDTO2);

		AuthDTO authDTO2 = new AuthDTO();
		authDTO2.setUsername(mockUser2.getUsername());
		authDTO2.setPassword(mockPassword);
		this.mockToken2 = "Bearer " + auth.login(authDTO2).getToken();

		auth.setTestAuth(mockToken);
	}

	@AfterEach
	void clean(@Autowired UserRepository userRepository) {
		userRepository.deleteById(mockUser.getId());
		userRepository.deleteById(mockUser2.getId());
	}

	@Test
	void contextLoads() {
		assertThat(mockUser).isNotNull();
		assertThat(mockToken).isNotNull();
		assertThat(mockUser2).isNotNull();
		assertThat(mockToken2).isNotNull();
	}

	@Test
	void retrieveFriendsNoAuth() throws Exception {
		mockMvc.perform(get("/api/friends?from=11"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveSpecificFriendNotFound() throws Exception {
		mockMvc.perform(get("/api/friends?from={id}&to={id}", -1, -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.from").value("Friend not found with these users"));
	}

	@Test
	void retrieveOutgoingFriends() throws Exception {
		mockMvc.perform(get("/api/friends?from={id}", mockUser.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrieveIncomingFriends() throws Exception {
		mockMvc.perform(get("/api/friends?to={id}", mockUser.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrieveSpecificFriend(@Autowired FriendService service) throws Exception {
		FriendCreateDTO friendCreateDTO = new FriendCreateDTO();
		friendCreateDTO.setFromId(mockUser.getId());
		friendCreateDTO.setToId(mockUser2.getId());
		service.createFriend(friendCreateDTO);
		mockMvc.perform(get("/api/friends?from={id}&to={id}", mockUser.getId(), mockUser2.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void addFriendNoAuth() throws Exception {
		FriendCreateDTO friendCreateDTO = new FriendCreateDTO();
		friendCreateDTO.setFromId(mockUser.getId());
		friendCreateDTO.setToId(mockUser2.getId());
		mockMvc.perform(post("/api/friends")
						.content(mapper.writeValueAsString(friendCreateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void addFriendAlreadyExists(@Autowired FriendService service) throws Exception {
		FriendCreateDTO friendCreateDTO = new FriendCreateDTO();
		friendCreateDTO.setFromId(mockUser.getId());
		friendCreateDTO.setToId(mockUser2.getId());
		service.createFriend(friendCreateDTO);
		mockMvc.perform(post("/api/friends")
						.content(mapper.writeValueAsString(friendCreateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void addFriendWithRequest(@Autowired FriendService service, @Autowired AuthService auth) throws Exception {
		FriendCreateDTO friendCreateDTO = new FriendCreateDTO();
		friendCreateDTO.setFromId(mockUser.getId());
		friendCreateDTO.setToId(mockUser2.getId());
		service.createFriend(friendCreateDTO);
		auth.setTestAuth(mockToken2);
		friendCreateDTO.setFromId(mockUser2.getId());
		friendCreateDTO.setToId(mockUser.getId());
		mockMvc.perform(post("/api/friends")
						.content(mapper.writeValueAsString(friendCreateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deleteFriendNoAuth() throws Exception {
		mockMvc.perform(delete("/api/friends/{id}", 1))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deleteFriendNotFound() throws Exception {
		mockMvc.perform(delete("/api/friends/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Friend not found with this id"));
	}

	@Test
	void deleteFriend(@Autowired FriendService service) throws Exception {
		FriendCreateDTO friendCreateDTO = new FriendCreateDTO();
		friendCreateDTO.setFromId(mockUser.getId());
		friendCreateDTO.setToId(mockUser2.getId());
		long id = service.createFriend(friendCreateDTO).getId();
		mockMvc.perform(delete("/api/friends/{id}", id)
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}
}
