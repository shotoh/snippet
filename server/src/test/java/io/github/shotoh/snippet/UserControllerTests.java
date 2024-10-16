package io.github.shotoh.snippet;

import io.github.shotoh.snippet.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
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

	@Value("${TEST_TOKEN:}")
	private String testToken;

	@Autowired
	public UserControllerTests(MockMvc mockMvc, UserController controller) {
		this.mockMvc = mockMvc;
		this.controller = controller;
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void retrieveUsersNoAuth() throws Exception {
		mockMvc.perform(get("/api/users"))
				.andExpect(status().isForbidden());
	}

	@Test
	void retrieveUsers() throws Exception {
		mockMvc.perform(get("/api/users")
						.header("Authorization", "Bearer " + testToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrieveUserNoAuth() throws Exception {
		mockMvc.perform(get("/api/users/{id}", 1))
				.andExpect(status().isForbidden());
	}

	@Test
	void retrieveUser() throws Exception {
		mockMvc.perform(get("/api/users/{id}", 1)
						.header("Authorization", "Bearer " + testToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(1),
						jsonPath("$.data.username").exists(),
						jsonPath("$.data.email").exists(),
						jsonPath("$.data.displayName").exists(),
						jsonPath("$.data.profilePicture").exists(),
						jsonPath("$.data.biography").exists());
	}

	@Test
	void retrieveUserNotFound() throws Exception {
		mockMvc.perform(get("/api/users/{id}", -1)
						.header("Authorization", "Bearer " + testToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void updateUserNoAuth() throws Exception {
		mockMvc.perform(patch("/api/users/{id}", 1))
				.andExpect(status().isForbidden());
	}

	@Test
	void updateUser() throws Exception {
		mockMvc.perform(patch("/api/users/{id}", 1)
						.header("Authorization", "Bearer " + testToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(1),
						jsonPath("$.data.username").exists(),
						jsonPath("$.data.email").exists(),
						jsonPath("$.data.displayName").exists(),
						jsonPath("$.data.profilePicture").exists(),
						jsonPath("$.data.biography").exists());
	}

	@Test
	void updateUserNotFound() throws Exception {
		mockMvc.perform(patch("/api/users/{id}", -1)
						.header("Authorization", "Bearer " + testToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}
}
