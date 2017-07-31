package unit;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.Application;
import control.WallController;
import implem.TestBase;

@RunWith(SpringRunner.class)
@TestExecutionListeners
public class WallControllerTest {
	private static final String WALL_URL = WallController.WALL_URI;
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		TestBase.createSampleUsers();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new WallController()).build();
	}

	@Test
	public void postToWall() throws Exception {

		// Normal, valid entry
		this.mockMvc.perform(post(WALL_URL + "/" + 1).content("Testing upload").accept(CONTENT_TYPE))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());
		this.mockMvc.perform(post(WALL_URL).param("id", "1").content("Testing upload").accept(CONTENT_TYPE))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());

		// Empty string content
		this.mockMvc.perform(post(WALL_URL + "/" + 1, "").contentType(CONTENT_TYPE).content("").accept(CONTENT_TYPE))
				.andExpect(status().isNoContent());
		this.mockMvc.perform(post(WALL_URL).param("id", "1").contentType(CONTENT_TYPE).content("").accept(CONTENT_TYPE))
				.andExpect(status().isNoContent());
	}

	@Test
	public void postToWallInvalid() throws Exception {
		// No content
		this.mockMvc.perform(post(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(status().isNoContent());
		this.mockMvc.perform(post(WALL_URL).param("id", "1").accept(CONTENT_TYPE)).andExpect(status().isNoContent());

		// Both id and param provided
		this.mockMvc.perform(post(WALL_URL + "/" + 1).param("id", "1").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Negative id provided
		this.mockMvc.perform(post(WALL_URL + "/" + -5).accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(post(WALL_URL).param("id", "-5").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());

		// Nan id provided
		this.mockMvc.perform(post(WALL_URL + "/Pichu").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(post(WALL_URL).param("id", "Pikachu").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Oversized
		this.mockMvc.perform(post(WALL_URL + "/" + 1).content(TestBase.createOversizedText())
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isPayloadTooLarge());
		this.mockMvc.perform(post(WALL_URL).param("id", "1").content(TestBase.createOversizedText())
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isPayloadTooLarge());

	}

	@Test
	public void getWall() throws Exception {
		// Existing user without posts
		this.mockMvc.perform(get(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(jsonPath("$.posts", hasSize(0)))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());
		this.mockMvc.perform(get(WALL_URL).param("id", "1").accept(CONTENT_TYPE))
				.andExpect(jsonPath("$.posts", hasSize(0))).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void getWallInvalid() throws Exception {
		// Nonexistent user
		this.mockMvc.perform(get(WALL_URL + "/" + 6171).accept(CONTENT_TYPE)).andExpect(status().isNotFound());
		this.mockMvc.perform(get(WALL_URL).param("id", "6171").accept(CONTENT_TYPE)).andExpect(status().isNotFound());

		// Param and path provided
		this.mockMvc.perform(get(WALL_URL + "/" + 1).param("id", "1").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Invalid id provided
		this.mockMvc.perform(get(WALL_URL + "/" + 0).accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL).param("id", "0").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL + "/" + -5).accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL + "/" + -5).accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL + "/12873612784512867451264").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL).param("id", "/12873612784512867451264").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL + "/Pikachu").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(WALL_URL).param("id", "/Pikachu").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
	}
}
