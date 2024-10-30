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
	private final ObjectMapper mapper;

	private UserDTO mockUser;
	private String mockToken;
	private PostDTO mockPost;

	@Autowired
	public PostControllerTests(MockMvc mockMvc, PostController controller) {
		this.mockMvc = mockMvc;
		this.controller = controller;
		this.mapper = new ObjectMapper();
	}

	@BeforeEach
	void setup(@Autowired PostService postService, @Autowired UserService userService,
	           @Autowired AuthService auth, @Value("${MOCK_PASSWORD:}") String mockPassword) {
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setUsername("mock1");
		userCreateDTO.setEmail("mock1@gmail.com");
		userCreateDTO.setPassword(mockPassword);
		this.mockUser = userService.createUser(userCreateDTO);

		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword(mockPassword);
		this.mockToken = "Bearer " + auth.login(authDTO).getToken();

		auth.setTestAuth(mockToken);

		PostCreateDTO postCreateDTO = new PostCreateDTO();
		postCreateDTO.setUserId(mockUser.getId());
		postCreateDTO.setContent("mock content1");
		this.mockPost = postService.createPost(postCreateDTO);
	}

	@AfterEach
	void clean(@Autowired PostRepository repository, @Autowired UserRepository userRepository) {
		repository.deleteById(mockPost.getId());
		userRepository.deleteById(mockUser.getId());
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(mockUser).isNotNull();
		assertThat(mockToken).isNotNull();
		assertThat(mockPost).isNotNull();
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
	void retrievePostsByUserNoAuth() throws Exception {
		mockMvc.perform(get("/api/posts?user={userId}", mockUser.getId()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrievePostsByUserNotFound() throws Exception {
		mockMvc.perform(get("/api/posts?user={userId}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void retrievePostsByUser() throws Exception {
		mockMvc.perform(get("/api/posts?user={userId}", mockUser.getId())
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
				.andExpect(jsonPath("$.data.id").value("Post not found with this id"));
	}

	@Test
	void retrievePost() throws Exception {
		mockMvc.perform(get("/api/posts/{id}", mockPost.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(mockPost.getId()),
						jsonPath("$.data.user").exists(),
						jsonPath("$.data.content").exists());
	}

	@Test
	void updatePostNoAuth() throws Exception {
		mockPost.setContent("new content");
		PostDTO updateDTO = new PostDTO();
		updateDTO.setContent(mockPost.getContent());
		mockMvc.perform(patch("/api/posts/{id}", mockPost.getId())
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updatePostNotFound() throws Exception {
		mockPost.setContent("new content");
		PostDTO updateDTO = new PostDTO();
		updateDTO.setContent(mockPost.getContent());
		mockMvc.perform(patch("/api/posts/{id}", -1)
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Post not found with this id"));
	}

	@Test
	void updatePost() throws Exception {
		mockPost.setContent("new content");
		PostDTO updateDTO = new PostDTO();
		updateDTO.setContent(mockPost.getContent());
		mockMvc.perform(patch("/api/posts/{id}", mockPost.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(mockPost.getId()),
						jsonPath("$.data.user.id").value(mockUser.getId()),
						jsonPath("$.data.content").value(mockPost.getContent()));
	}

	@Test
	void updatePostInvalidContent() throws Exception {
		mockPost.setContent("");
		PostDTO updateDTO = new PostDTO();
		updateDTO.setContent(mockPost.getContent());
		mockMvc.perform(patch("/api/posts/{id}", mockUser.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.content").value("size must be between 1 and 1023"));
	}

	@Test
	void deletePostNoAuth() throws Exception {
		mockMvc.perform(delete("/api/posts/{id}", mockPost.getId()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deletePostNotFound() throws Exception {
		mockMvc.perform(delete("/api/posts/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Post not found with this id"));
	}

	@Test
	void deletePost() throws Exception {
		mockMvc.perform(delete("/api/posts/{id}", mockPost.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}
}
