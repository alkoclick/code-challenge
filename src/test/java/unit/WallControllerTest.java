package unit;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import control.WallController;
import okhttp3.OkHttpClient;
import util.Messages;

@RunWith(SpringRunner.class)
@TestExecutionListeners
public class WallControllerTest {
	private static OkHttpClient client;
	private static ObjectMapper mapper;
	private static final String WALL_URL = Messages.getString("URI.Wall");
	private static final String SERVER_URL = Messages.getString("URI.Localhost");
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		client = new OkHttpClient();
		mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new WallController()).alwaysExpect(status().isOk())
				.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).build();
	}

	@Test
	public void testPost() {

	}

	@Test
	public void getWall() throws Exception {
		this.mockMvc.perform(get(WALL_URL).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.user.username").value("Alex")).andExpect(jsonPath("$.posts", hasSize(1)));
	}
}
