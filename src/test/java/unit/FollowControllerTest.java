package unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

		// One with content
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1 + "/" + 2).content("Mewtwo").accept(CONTENT_TYPE))
				.andExpect(status().isOk());

		// Invalid paths
		this.mockMvc.perform(post(FOLLOW_URL + "/" + -1 + "/" + -1).accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/" + 1 + "/" + -1).accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post(FOLLOW_URL + "/" + -1 + "/" + 1).content("Mewtwo").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Invalid params
		this.mockMvc.perform(post(FOLLOW_URL).param("idFollower", "-1").param("idFollowee", "-2").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Non-numerals

	}

}
