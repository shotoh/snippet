package io.github.shotoh.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shotoh.snippet.controllers.UserController;
import io.github.shotoh.snippet.models.auth.AuthDTO;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import io.github.shotoh.snippet.services.AuthService;
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
public class UserControllerTests {
	private final MockMvc mockMvc;
	private final UserController controller;
	private final UserService service;
	private final ObjectMapper mapper;
	private final UserDTO mockUser;
	private final String mockToken;

	@Autowired
	public UserControllerTests(MockMvc mockMvc, UserController controller, UserService service,
	                           AuthService auth, @Value("${MOCK_PASSWORD:}") String mockPassword) {
		this.mockMvc = mockMvc;
		this.controller = controller;
		this.service = service;
		this.mapper = new ObjectMapper();

		UserDTO mockDTO = service.getUserByUsername("mock1");
		if (mockDTO == null) {
			UserCreateDTO createDTO = new UserCreateDTO();
			createDTO.setUsername("mock1");
			createDTO.setEmail("mock1@gmail.com");
			createDTO.setPassword(mockPassword);
			mockDTO = service.createUser(createDTO);
		}
		this.mockUser = mockDTO;
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUsername(mockUser.getUsername());
		authDTO.setPassword(mockPassword);
		this.mockToken = "Bearer " + auth.login(authDTO).getToken();
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
		assertThat(mockUser).isNotNull();
		assertThat(mockToken).isNotNull();
	}

	@Test
	void retrieveUsersNoAuth() throws Exception {
		mockMvc.perform(get("/api/users"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveUsers() throws Exception {
		mockMvc.perform(get("/api/users")
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrieveUserNoAuth() throws Exception {
		mockMvc.perform(get("/api/users/{id}", 1))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveUser() throws Exception {
		mockMvc.perform(get("/api/users/{id}", 1)
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
	void retrieveUserNotFound() throws Exception {
		mockMvc.perform(get("/api/users/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void updateUserNoAuth() throws Exception {
		mockUser.setBiography("new bio");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/users/{id}", mockUser.getId())
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updateUser() throws Exception {
		mockUser.setBiography("new bio");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/users/{id}", mockUser.getId())
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
	void updateUserNotFound() throws Exception {
		mockUser.setBiography("new bio");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/users/{id}", -1)
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void deleteUserNoAuth() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", mockUser.getId()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deleteUser() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", mockUser.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	void deleteUserNotFound() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}
}
