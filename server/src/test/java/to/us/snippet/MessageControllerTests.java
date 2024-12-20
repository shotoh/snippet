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
import to.us.snippet.messages.MessageCreateDTO;
import to.us.snippet.messages.MessageDTO;
import to.us.snippet.messages.MessageService;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.users.UserDTO;
import to.us.snippet.users.UserRepository;
import to.us.snippet.users.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper mapper;

	private UserDTO mockUser;
	private String mockToken;
	private UserDTO mockUser2;
	private String mockToken2;
	private MessageDTO mockMessage;

	@Autowired
	public MessageControllerTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
		this.mapper = new ObjectMapper();
	}

	@BeforeEach
	void setup(@Autowired MessageService service, @Autowired FriendService friendService,
	           @Autowired UserService userService, @Autowired AuthService auth,
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
		FriendCreateDTO friendCreateDTO = new FriendCreateDTO();
		friendCreateDTO.setFromId(mockUser.getId());
		friendCreateDTO.setToId(mockUser2.getId());
		friendService.createFriend(friendCreateDTO);
		auth.setTestAuth(mockToken2);
		friendCreateDTO.setFromId(mockUser2.getId());
		friendCreateDTO.setToId(mockUser.getId());
		friendService.createFriend(friendCreateDTO);

		auth.setTestAuth(mockToken);

		MessageCreateDTO messageCreateDTO = new MessageCreateDTO();
		messageCreateDTO.setFromId(mockUser.getId());
		messageCreateDTO.setToId(mockUser2.getId());
		messageCreateDTO.setContent("mock message1");
		this.mockMessage = service.createMessage(messageCreateDTO);
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
		assertThat(mockMessage).isNotNull();
	}

	@Test
	void retrieveMessagesNoAuth() throws Exception {
		mockMvc.perform(get("/api/messages?from={id}&to={id}", 1, 2))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveZeroMessages() throws Exception {
		mockMvc.perform(get("/api/messages?from={id}&to={id}", mockUser.getId(), -1)
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void retrieveMessages() throws Exception {
		mockMvc.perform(get("/api/messages?from={id}&to={id}", mockUser.getId(), mockUser2.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void createMessageNoAuth() throws Exception {
		MessageCreateDTO messageCreateDTO = new MessageCreateDTO();
		messageCreateDTO.setToId(mockUser2.getId());
		messageCreateDTO.setContent("mock message2");
		mockMvc.perform(post("/api/messages")
						.content(mapper.writeValueAsString(messageCreateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void createMessage() throws Exception {
		MessageCreateDTO messageCreateDTO = new MessageCreateDTO();
		messageCreateDTO.setToId(mockUser2.getId());
		messageCreateDTO.setContent("mock message2");
		mockMvc.perform(post("/api/messages")
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(messageCreateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data.content").value("mock message2"));
	}

	@Test
	void createMessageInvalidContent() throws Exception {
		MessageCreateDTO messageCreateDTO = new MessageCreateDTO();
		messageCreateDTO.setToId(mockUser2.getId());
		messageCreateDTO.setContent("");
		mockMvc.perform(post("/api/messages")
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(messageCreateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.content").value("size must be between 1 and 1023"));
	}

	@Test
	void updateMessageNoAuth() throws Exception {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setContent("new message");
		mockMvc.perform(patch("/api/messages/{id}", mockMessage.getId())
						.content(mapper.writeValueAsString(messageDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updateMessageNotFound() throws Exception {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setContent("new message");
		mockMvc.perform(patch("/api/messages/{id}", -1)
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(messageDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Message not found with this id"));
	}

	@Test
	void updateMessage() throws Exception {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setContent("new message");
		mockMvc.perform(patch("/api/messages/{id}", mockMessage.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(messageDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(mockMessage.getId()),
						jsonPath("$.data.content").value(messageDTO.getContent()));
	}

	@Test
	void updateMessageInvalidContent() throws Exception {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setContent("");
		mockMvc.perform(patch("/api/messages/{id}", mockMessage.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(messageDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.content").value("size must be between 1 and 1023"));
	}

	@Test
	void deleteMessageNoAuth() throws Exception {
		mockMvc.perform(delete("/api/messages/{id}", 1))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deleteMessageNotFound() throws Exception {
		mockMvc.perform(delete("/api/messages/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Message not found with this id"));
	}

	@Test
	void deleteMessage() throws Exception {
		mockMvc.perform(delete("/api/messages/{id}", mockMessage.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}
}
