package io.github.shotoh.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shotoh.snippet.controllers.PostController;
import io.github.shotoh.snippet.models.auth.AuthDTO;
import io.github.shotoh.snippet.models.posts.PostCreateDTO;
import io.github.shotoh.snippet.models.posts.PostDTO;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import io.github.shotoh.snippet.services.AuthService;
import io.github.shotoh.snippet.services.PostService;
import io.github.shotoh.snippet.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
	private final ObjectMapper mapper;
	private final UserDTO mockUser;
	private final String mockToken;
	private final PostDTO mockPost;

	@Autowired
	public PostControllerTests(MockMvc mockMvc, PostController controller, PostService service, UserService userService,
	                           AuthService auth, @Value("${MOCK_PASSWORD:}") String mockPassword) {
		this.mockMvc = mockMvc;
		this.controller = controller;
		this.service = service;
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
						jsonPath("$.data.profilePicture").value(mockUser.getProfilePicture()),
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
