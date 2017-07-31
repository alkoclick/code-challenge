package implem;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.Application;
import control.FollowController;
import control.TimelineController;
import control.WallController;

public class WallControllerImplemTest {
	private static final String WALL_URL = WallController.WALL_URI;
	private static final String FOLLOWS_URL = FollowController.FOLLOWS_URL;
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private static final String TIMELINE_URL = TimelineController.TIMELINE_URL;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(new WallController(), new TimelineController(), new FollowController()).build();
	}

	@Test
	public void postAndUpdateWall() throws Exception {
		int userId = 123;
		// Get empty wall
		this.mockMvc.perform(get(WALL_URL + "/" + userId).accept(CONTENT_TYPE)).andExpect(status().isNotFound());

		// Post a normal message, should create user
		this.mockMvc.perform(post(WALL_URL + "/" + userId).content("Testing upload").accept(CONTENT_TYPE))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());

		// Get wall length 1
		this.mockMvc.perform(get(WALL_URL + "/" + userId).accept(CONTENT_TYPE))
				.andExpect(jsonPath("$.posts", hasSize(1))).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(status().isOk());

		// Post an oversized
		this.mockMvc.perform(post(WALL_URL + "/" + userId).content(TestBase.createOversizedText())
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isPayloadTooLarge());

		// Get wall length 1
		this.mockMvc.perform(get(WALL_URL + "/" + userId).accept(CONTENT_TYPE))
				.andExpect(jsonPath("$.posts", hasSize(1))).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void createUserAndFollow() throws Exception {
		int userId = 50;
		int followerId = 51;
		int spamPosts = 10;

		// Let's make a user and make him post some stuff
		for (int i = 0; i < spamPosts; i++) {
			this.mockMvc.perform(post(WALL_URL + "/" + userId).content("Spam " + i).accept(CONTENT_TYPE))
					.andExpect(status().isOk());
		}

		// Let's create the follower
		this.mockMvc.perform(post(WALL_URL + "/" + followerId).content("Zapdos").accept(CONTENT_TYPE))
				.andExpect(status().isOk());

		// Make sure they have both been created
		this.mockMvc.perform(get(WALL_URL + "/" + userId).accept(CONTENT_TYPE))
				.andExpect(jsonPath("$.posts", hasSize(spamPosts))).andExpect(status().isOk());
		this.mockMvc.perform(get(WALL_URL + "/" + followerId).accept(CONTENT_TYPE))
				.andExpect(jsonPath("$.posts", hasSize(1))).andExpect(status().isOk());

		// Make the follower follow user
		this.mockMvc.perform(post(FOLLOWS_URL + "/" + followerId + "/" + userId).accept(CONTENT_TYPE))
				.andExpect(status().isOk());

		this.mockMvc.perform(get(TIMELINE_URL + "/" + followerId).accept(CONTENT_TYPE)).andDo(print())
				.andExpect(jsonPath("$", hasSize(spamPosts))).andExpect(status().isOk());
	}
}
