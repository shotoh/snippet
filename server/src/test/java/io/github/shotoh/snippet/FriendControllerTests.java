package io.github.shotoh.snippet;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.shotoh.snippet.controllers.FriendController;
import io.github.shotoh.snippet.controllers.UserController;
import io.github.shotoh.snippet.models.auth.AuthDTO;
import io.github.shotoh.snippet.models.friends.FriendCreateDTO;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import io.github.shotoh.snippet.services.AuthService;
import io.github.shotoh.snippet.services.FriendService;
import io.github.shotoh.snippet.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerTests {
	private final MockMvc mockMvc;
	private final FriendController controller;
	private final UserController uController;
	private final FriendService service;
	private final UserService uService;
	private final ObjectMapper mapper;
	private final UserDTO mockUser;		
	private final String mockToken;

	@Autowired
	public FriendControllerTests(MockMvc mockMvc, UserController uController, FriendController controller, UserService uService,
	                           FriendService service, AuthService auth, @Value("${MOCK_PASSWORD:}") String mockPassword) {
		this.mockMvc = mockMvc;
		this.uController = uController;
		this.controller = controller;
		this.uService = uService;
		this.service = service;
		this.mapper = new ObjectMapper();

		UserDTO mockDTO = uService.getUserByUsername("mock1");
		if (mockDTO == null) {
			UserCreateDTO createDTO = new UserCreateDTO();
			createDTO.setUsername("mock1");
			createDTO.setEmail("mock1@gmail.com");
			createDTO.setPassword(mockPassword);
			mockDTO = uService.createUser(createDTO);
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
		assertThat(uController).isNotNull();
		assertThat(uService).isNotNull();
	}

	@Test
	void retrieveFriendsNoAuth() throws Exception {
		mockMvc.perform(get("/api/friends"))
				.andExpect(status().isUnauthorized());
	}

	//Create Friend Test
	@Test
	void createFriend() throws Exception {
		FriendCreateDTO createDTO = new FriendCreateDTO(); 
		createDTO.setToId(2L); 

		mockMvc.perform(post("/api/friends"))
				.header("Authorization", mockToken)
				.content(mapper.writeValueAsString(createDTO))
				.content(MediaType.APPLICATION_JSON)
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value("success"))
			.andExpect(jsonPath("$.data.fromId").exists())
			.andExpect(jsonPath("$.data.toId").value(2L));
	}

	//Retrieve Friend by ID Test
	@Test
	void retrieveFriend() throws Exception {
		mockMvc.perform(get("/api/friends/{id}", 1)
				.header("Authorization", mockToken))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value("success"))
			.andExpect(jsonPath("$.data.id").value(1))
			.andExpect(jsonPath("$.data.fromId").exists())
			.andExpect(jsonPath("$.data.toId").exists());
	}

	//Tests Friend Retrieval using fromID
	@Test
	void retrieveFriendsByFromId() throws Exception {
	    mockMvc.perform(get("/api/friends")
			    .header("Authorization", mockToken))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.status").value("success"))
		    .andExpect(jsonPath("$.data").isArray());
	}

	//Tests Friend Retrieval using fromID and toID
	@Test
	void retrieveFriendByFromAndTo() throws Exception {
	    long fromId = 1L; 
	    long toId = 2L;  
    
	    mockMvc.perform(get("/api/friends/from/{fromId}/to/{toId}", fromId, toId)
			    .header("Authorization", mockToken))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.status").value("success"))
		    .andExpect(jsonPath("$.data.fromId").value(fromId))
		    .andExpect(jsonPath("$.data.toId").value(toId))
		    .andExpect(jsonPath("$.data.status").value("PENDING")); 
	}
    
	//Tests Friend Removal
	@Test
	void deleteFriend() throws Exception {
	    mockMvc.perform(delete("/api/friends/{id}", 1)
			    .header("Authorization", mockToken))
		    .andExpect(status().isNoContent());
	}
    
	/*//Tests Friend Retrieval when Friend Doesn't Exist
	@Test
	void retrieveFriendNotFound() throws Exception {
	    mockMvc.perform(get("/api/friends/{id}", -1)
			    .header("Authorization", mockToken))
		    .andExpect(status().isNotFound())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.status").value("fail"))
		    .andExpect(jsonPath("$.data.id").value("Friend not found with this id"));
	}*/
   
	/*//Tests Friend Adding if Friend is Already Added
	@Test
	void createFriendAlreadyExists() throws Exception {
	    FriendCreateDTO createDTO = new FriendCreateDTO();
	    createDTO.setToId(2L); // Assuming friend already exists
    
	    mockMvc.perform(post("/api/friends")
			    .header("Authorization", mockToken)
			    .content(mapper.writeValueAsString(createDTO))
			    .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isConflict())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.status").value("fail"))
		    .andExpect(jsonPath("$.data.fromId").value("Friend already exists with this user"));
	}*/
    
}
