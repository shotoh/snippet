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
import to.us.snippet.comments.CommentCreateDTO;
import to.us.snippet.comments.CommentDTO;
import to.us.snippet.comments.CommentRepository;
import to.us.snippet.comments.CommentService;
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
public class CommentControllerTests {
	private final MockMvc mockMvc;
	private final ObjectMapper mapper;

	private UserDTO mockUser;
	private String mockToken;
	private PostDTO mockPost;
	private CommentDTO mockComment;

	@Autowired
	public CommentControllerTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
		this.mapper = new ObjectMapper();
	}

	@BeforeEach
	void setup(@Autowired CommentService commentService, @Autowired PostService postService,
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

		auth.setTestAuth(mockToken);

		PostCreateDTO postCreateDTO = new PostCreateDTO();
		postCreateDTO.setUserId(mockUser.getId());
		postCreateDTO.setContent("mock content1");
		this.mockPost = postService.createPost(postCreateDTO);

		CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
		commentCreateDTO.setUserId(mockUser.getId());
		commentCreateDTO.setPostId(mockPost.getId());
		commentCreateDTO.setContent("mock comment1");
		this.mockComment = commentService.createComment(commentCreateDTO);
	}

	@AfterEach
	void clean(@Autowired CommentRepository repository, @Autowired PostRepository postRepository,
	           @Autowired UserRepository userRepository) {
		repository.deleteById(mockComment.getId());
		postRepository.deleteById(mockPost.getId());
		userRepository.deleteById(mockUser.getId());
	}

	@Test
	void contextLoads() {
		assertThat(mockUser).isNotNull();
		assertThat(mockToken).isNotNull();
		assertThat(mockPost).isNotNull();
		assertThat(mockComment).isNotNull();
	}

	@Test
	void retrieveCommentsNoAuth() throws Exception {
		mockMvc.perform(get("/api/comments"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveComments() throws Exception {
		mockMvc.perform(get("/api/comments")
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrieveCommentsByPostNoAuth() throws Exception {
		mockMvc.perform(get("/api/comments?post={postId}", mockPost.getId()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveCommentsByPostNotFound() throws Exception {
		mockMvc.perform(get("/api/comments?post={postId}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void retrieveCommentsByPost() throws Exception {
		mockMvc.perform(get("/api/comments?post={postId}", mockPost.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void retrieveCommentNoAuth() throws Exception {
		mockMvc.perform(get("/api/comments/{id}", 1))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void retrieveCommentNotFound() throws Exception {
		mockMvc.perform(get("/api/comments/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Comment not found with this id"));
	}

	@Test
	void retrieveComment() throws Exception {
		mockMvc.perform(get("/api/comments/{id}", mockComment.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(mockComment.getId()),
						jsonPath("$.data.user").exists(),
						jsonPath("$.data.post").exists(),
						jsonPath("$.data.content").exists());
	}

	@Test
	void updateCommentNoAuth() throws Exception {
		mockComment.setContent("new comment");
		CommentDTO updateDTO = new CommentDTO();
		updateDTO.setContent(mockComment.getContent());
		mockMvc.perform(patch("/api/comments/{id}", mockComment.getId())
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updateCommentNotFound() throws Exception {
		mockComment.setContent("new comment");
		CommentDTO updateDTO = new CommentDTO();
		updateDTO.setContent(mockComment.getContent());
		mockMvc.perform(patch("/api/comments/{id}", -1)
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Comment not found with this id"));
	}

	@Test
	void updateComment() throws Exception {
		mockComment.setContent("new comment");
		CommentDTO updateDTO = new CommentDTO();
		updateDTO.setContent(mockComment.getContent());
		mockMvc.perform(patch("/api/comments/{id}", mockComment.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpectAll(
						jsonPath("$.data.id").value(mockComment.getId()),
						jsonPath("$.data.user.id").value(mockUser.getId()),
						jsonPath("$.data.post.id").value(mockPost.getId()),
						jsonPath("$.data.content").value(mockComment.getContent()));
	}

	@Test
	void updateCommentInvalidContent() throws Exception {
		mockComment.setContent("");
		CommentDTO updateDTO = new CommentDTO();
		updateDTO.setContent(mockComment.getContent());
		mockMvc.perform(patch("/api/comments/{id}", mockUser.getId())
						.header("Authorization", mockToken)
						.content(mapper.writeValueAsString(updateDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.content").value("size must be between 1 and 1023"));
	}

	@Test
	void deleteCommentNoAuth() throws Exception {
		mockMvc.perform(delete("/api/comments/{id}", mockComment.getId()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void deleteCommentNotFound() throws Exception {
		mockMvc.perform(delete("/api/comments/{id}", -1)
						.header("Authorization", mockToken))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("fail"))
				.andExpect(jsonPath("$.data.id").value("Comment not found with this id"));
	}

	@Test
	void deleteComment() throws Exception {
		mockMvc.perform(delete("/api/comments/{id}", mockComment.getId())
						.header("Authorization", mockToken))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").doesNotExist());
	}
}
