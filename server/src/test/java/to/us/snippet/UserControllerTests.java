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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import to.us.snippet.auth.AuthDTO;
import to.us.snippet.auth.AuthService;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.users.UserDTO;
import to.us.snippet.users.UserRepository;
import to.us.snippet.users.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper mapper;

	private UserDTO mockUser;
	private String mockToken;

	@Autowired
	public UserControllerTests(MockMvc mockMvc) {
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
	}

	@AfterEach
	void clean(@Autowired UserRepository repository) {
		repository.deleteById(mockUser.getId());
	}

	@Test
	void contextLoads() {
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
	void retrieveUserNotFound() throws Exception {
		mockMvc.perform(get("/api/users/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
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
						jsonPath("$.data.biography").value(mockUser.getBiography()));
	}

	@Test
	void updateUserInvalidDisplayName() throws Exception {
		mockUser.setDisplayName("");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setDisplayName(mockUser.getDisplayName());
		mockMvc.perform(patch("/api/users/{id}", mockUser.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.displayName").value("size must be between 1 and 31"));
	}

	@Test
	void updateUserInvalidBiography() throws Exception {
		mockUser.setBiography("");
		UserDTO updateDTO = new UserDTO();
		updateDTO.setBiography(mockUser.getBiography());
		mockMvc.perform(patch("/api/users/{id}", mockUser.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.biography").value("size must be between 1 and 1023"));
	}

	@Test
	void updatePictureNoAuth() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"test.png",
				MediaType.IMAGE_PNG_VALUE,
				"a".getBytes()
		);
		mockMvc.perform(multipart("/api/users/{id}/profile-picture", mockUser.getId())
						.file(file)
						.with(request -> {
							request.setMethod("PATCH");
							return request;
						}))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updatePictureNotFound() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"test.png",
				MediaType.IMAGE_PNG_VALUE,
				"a".getBytes()
		);
		mockMvc.perform(multipart("/api/users/{id}/profile-picture", -1)
						.file(file)
						.with(request -> {
							request.setMethod("PATCH");
							return request;
						})
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("User not found with this id"));
	}

	@Test
	void updatePictureEmptyFile() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"test.png",
				MediaType.IMAGE_PNG_VALUE,
				new byte[0]
		);
		mockMvc.perform(multipart("/api/users/{id}/profile-picture", mockUser.getId())
						.file(file)
						.with(request -> {
							request.setMethod("PATCH");
							return request;
						})
						.header("Authorization", mockToken))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.file").value("File is empty"));
	}

	@Test
	void updatePictureNotImage() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"test.png",
				MediaType.TEXT_PLAIN_VALUE,
				"a".getBytes()
		);
		mockMvc.perform(multipart("/api/users/{id}/profile-picture", mockUser.getId())
						.file(file)
						.with(request -> {
							request.setMethod("PATCH");
							return request;
						})
						.header("Authorization", mockToken))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.file").value("File is not an image"));
	}

	@Test
	void updatePictureInvalidFilename() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"",
				MediaType.IMAGE_PNG_VALUE,
				"a".getBytes()
		);
		mockMvc.perform(multipart("/api/users/{id}/profile-picture", mockUser.getId())
						.file(file)
						.with(request -> {
							request.setMethod("PATCH");
							return request;
						})
						.header("Authorization", mockToken))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.file").value("Invalid file name"));
	}

	@Test
	void updatePicture() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"test.png",
				MediaType.IMAGE_PNG_VALUE,
				"a".getBytes()
		);
		mockMvc.perform(multipart("/api/users/{id}/profile-picture", mockUser.getId())
						.file(file)
						.with(request -> {
							request.setMethod("PATCH");
							return request;
						})
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent());
	}

	@Test
	void deleteUserNoAuth() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", mockUser.getId()))
				.andExpect(status().isUnauthorized());
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

	@Test
	void deleteUser() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", mockUser.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}
}
