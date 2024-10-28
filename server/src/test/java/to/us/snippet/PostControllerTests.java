package to.us.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import to.us.snippet.auth.AuthDTO;
import to.us.snippet.auth.AuthService;
import to.us.snippet.posts.PostController;
import to.us.snippet.posts.PostCreateDTO;
import to.us.snippet.posts.PostDTO;
import to.us.snippet.posts.PostRepository;
import to.us.snippet.posts.PostService;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.users.UserDTO;
import to.us.snippet.users.UserRepository;
import to.us.snippet.users.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTests {
	private final MockMvc mockMvc;
	private final PostController controller;
	private final PostService service;
	private final PostRepository repository;
	private final UserRepository userRepository;
	private final ObjectMapper mapper;
	private final UserDTO mockUser;
	private final String mockToken;
	private final PostDTO mockPost;

	@Autowired
	public PostControllerTests(MockMvc mockMvc, PostController controller, PostService service, PostRepository repository,
	                           UserRepository userRepository, UserService userService,
	                           AuthService auth, @Value("${MOCK_PASSWORD:}") String mockPassword) {
		this.mockMvc = mockMvc;
		this.controller = controller;
		this.service = service;
		this.repository = repository;
		this.userRepository = userRepository;
		this.mapper = new ObjectMapper();

		UserCreateDTO createDTO = new UserCreateDTO();
		createDTO.setUsername("mock1");
		createDTO.setEmail("mock1@gmail.com");
		createDTO.setPassword(mockPassword);
		this.mockUser = userService.createUser(createDTO);
		
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword(mockPassword);
		this.mockToken = "Bearer " + auth.login(authDTO).getToken();

		PostCreateDTO postCreateDTO = new PostCreateDTO();
		postCreateDTO.setUserId(mockUser.getId());
		postCreateDTO.setContent("mock content1");
		this.mockPost = service.createPost(postCreateDTO);
	}

	@AfterEach
	void clean() {
		repository.deleteById(mockPost.getId());
		userRepository.deleteById(mockUser.getId());
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
		assertThat(mockUser).isNotNull();
		assertThat(mockPost).isNotNull();
		assertThat(mockToken).isNotNull();
	}

	@Test
	void retrievePostsNoAuth() throws Exception {
		mockMvc.perform(get("/api/posts"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrievePosts() throws Exception {
		mockMvc.perform(get("/api/posts")
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrievePostNoAuth() throws Exception {
		mockMvc.perform(get("/api/posts/{id}", 1))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrievePostNotFound() throws Exception {
		mockMvc.perform(get("/api/posts/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void retrievePost() throws Exception {
		mockMvc.perform(get("/api/posts/{id}", 1)
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(1),
						jsonPath("$.data.username").exists(),
						jsonPath("$.data.email").exists());
	}

	@Test
	void updatePostNoAuth() throws Exception {
		mockUser.setBiography("new bio");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/posts/{id}", mockUser.getId())
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updatePostNotFound() throws Exception {
		mockUser.setBiography("new bio");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/posts/{id}", -1)
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void updatePost() throws Exception {
		mockUser.setBiography("new bio");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/posts/{id}", mockUser.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(mockUser.getId()),
						jsonPath("$.data.username").value(mockUser.getUsername()),
						jsonPath("$.data.email").value(mockUser.getEmail()),
						jsonPath("$.data.displayName").value(mockUser.getDisplayName()),
						jsonPath("$.data.biography").value(mockUser.getBiography()));
	}

	@Test
	void updatePostInvalidDisplayName() throws Exception {
		mockUser.setDisplayName("");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setDisplayName(mockUser.getDisplayName());
		mockMvc.perform(patch("/api/posts/{id}", mockUser.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.displayName").value("size must be between 1 and 31"));
	}

	@Test
	void deletePostNoAuth() throws Exception {
		mockMvc.perform(delete("/api/posts/{id}", mockUser.getId()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deletePostNotFound() throws Exception {
		mockMvc.perform(delete("/api/posts/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void deletePost() throws Exception {
		mockMvc.perform(delete("/api/posts/{id}", mockUser.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}
}
