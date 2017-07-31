package implem;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import control.WallController;
import okhttp3.OkHttpClient;
import util.Messages;

public class WallControllerImplemTest {
	private static final String WALL_URL = Messages.getString("URI.Wall");
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private static OkHttpClient client;
	private static ObjectMapper mapper;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		client = new OkHttpClient();
		mapper = new ObjectMapper();
		TestBase.createSampleUsers();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new WallController()).build();
	}

	@Test
	public void postAndUpdateWall() throws Exception {
		// Get empty wall
		this.mockMvc.perform(get(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(jsonPath("$.posts", hasSize(0)))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());

		// Post a normal message
		this.mockMvc.perform(post(WALL_URL + "/" + 1).content("Testing upload").accept(CONTENT_TYPE))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());

		// Get wall length 1
		this.mockMvc.perform(get(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(jsonPath("$.posts", hasSize(1)))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());

		// Post an oversized
		this.mockMvc
				.perform(post(WALL_URL + "/" + 1)
						.content(
								"This is a text that will have more than 140 characters This is a text that will have more than 140 characters This is a text that will have more than 140 characters")
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isPayloadTooLarge());

		// Get wall length 1
		this.mockMvc.perform(get(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(jsonPath("$.posts", hasSize(1)))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());
	}
}
