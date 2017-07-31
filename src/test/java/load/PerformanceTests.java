package load;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.Application;
import control.FollowController;
import control.TimelineController;
import control.WallController;

public class PerformanceTests {

	private static final String WALL_URL = WallController.WALL_URI;
	private static final String FOLLOWS_URL = FollowController.FOLLOWS_URL;
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private static final String TIMELINE_URL = TimelineController.TIMELINE_URL;

	private MockMvc mockMvc;
	private static final int count = 10000;
	Random random = new Random(count);

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(new WallController(), new TimelineController(), new FollowController()).build();
	}

	@Test
	public void spamPosts() throws Exception {

	}

	@Test
	public void spamPostsAndTimelines() throws Exception {

	}
}
