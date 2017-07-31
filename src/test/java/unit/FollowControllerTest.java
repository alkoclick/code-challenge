package unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.Application;
import control.FollowController;
import implem.TestBase;

public class FollowControllerTest {
	private static final String FOLLOW_URL = FollowController.FOLLOWS_URL;
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		TestBase.createSampleUsers();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new FollowController()).build();
	}

	@Test
	public void postFollows() throws Exception {
		// Normal, valid entry
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1 + "/" + 2).accept(CONTENT_TYPE)).andExpect(status().isOk());
		this.mockMvc.perform(post(FOLLOW_URL).param("idFollower", "1").param("idFollowee", "2").accept(CONTENT_TYPE))
				.andExpect(status().isOk());

		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1).param("idFollowee", "2").accept(CONTENT_TYPE))
				.andExpect(status().isOk());

		// One with content
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1 + "/" + 2).content("Mewtwo").accept(CONTENT_TYPE))
				.andExpect(status().isOk());

	}

	@Test
	public void postFollowsInvalid() throws Exception {
		// Not enough params provided
		this.mockMvc.perform(post(FOLLOW_URL).accept(CONTENT_TYPE)).andDo(print()).andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1).accept(CONTENT_TYPE)).andDo(print())
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL).param("idFollower", "1").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Both path and query params provided for follower/followee
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1).param("idFollower", "1").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1 + "/" + 2).param("idFollower", "1").param("idFollowee", "2")
				.accept(CONTENT_TYPE)).andExpect(status().isBadRequest());

		// Invalid paths
		this.mockMvc.perform(post(FOLLOW_URL + "/" + -5 + "/" + -1).accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1 + "/" + -5).accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/" + -5 + "/" + 1).accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Invalid params
		this.mockMvc.perform(post(FOLLOW_URL).param("idFollower", "-1").param("idFollowee", "-2").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL).param("idFollower", "1").param("idFollowee", "-2").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL).param("idFollower", "-1").param("idFollowee", "2").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Non-numerals
		this.mockMvc
				.perform(post(FOLLOW_URL).param("idFollower", "Mew").param("idFollowee", "Mewtwo").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/Mew/Mewtwo").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/1/Mewtwo").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/Mew/2").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
	}

}
