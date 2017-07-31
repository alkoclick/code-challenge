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

import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import control.WallController;
import implem.TestBase;
import okhttp3.OkHttpClient;
import util.Messages;

@RunWith(SpringRunner.class)
@TestExecutionListeners
public class WallControllerTest {
	private static OkHttpClient client;
	private static ObjectMapper mapper;
	private static final String WALL_URL = Messages.getString("URI.Wall");
	private static final String SERVER_URL = Messages.getString("URI.Localhost");
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		client = new OkHttpClient();
		mapper = new ObjectMapper();
		TestBase.initializeData();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new WallController()).build();
	}

	@Test
	public void testPostToWall() throws Exception {

		// Normal, valid entry
		this.mockMvc.perform(post(WALL_URL + "/" + 1).content("Testing upload").accept(CONTENT_TYPE))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());

		this.mockMvc.perform(post(WALL_URL + "/" + 1, "").contentType(CONTENT_TYPE).content("").accept(CONTENT_TYPE))
				.andExpect(status().isNoContent());

		this.mockMvc.perform(post(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(status().isNoContent());

		this.mockMvc
				.perform(post(WALL_URL + "/" + 1)
						.content(
								"This is a text that will have more than 140 characters This is a text that will have more than 140 characters This is a text that will have more than 140 characters")
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isPayloadTooLarge());

	}

	@Test
	public void getWall() throws Exception {
		this.mockMvc.perform(get(WALL_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(jsonPath("$.posts", hasSize(0)))
				.andExpect(status().isOk());

		// this.mockMvc.perform(get(WALL_URL + "/" +
		// 2).accept(MediaType.APPLICATION_JSON_UTF8))
		// .andExpect(jsonPath("$.user.username").value("Mateusz")).andExpect(jsonPath("$.posts",
		// hasSize(1)));

		// this.mockMvc.perform(get(WALL_URL + "/" +
		// 3).accept(MediaType.APPLICATION_JSON_UTF8))
		// .andExpect(jsonPath("$.user.username").value("Guilherme")).andExpect(jsonPath("$.posts",
		// hasSize(1)));
	}
}
