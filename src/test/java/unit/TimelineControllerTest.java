package unit;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.Application;
import control.TimelineController;
import implem.TestBase;

@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TimelineControllerTest {
	private static final String TIMELINE_URL = TimelineController.TIMELINE_URL;
	private static final MediaType CONTENT_TYPE = Application.CONTENT_TYPE;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		TestBase.createSampleUsers();
		TestBase.createSamplePosts();
		TestBase.createFollows();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new TimelineController()).build();
	}

	@Test
	public void getTimeline() throws Exception {
		// Valid users
		this.mockMvc.perform(get(TIMELINE_URL + "/" + 1).accept(CONTENT_TYPE)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().contentType(CONTENT_TYPE)).andExpect(status().isOk());
		this.mockMvc.perform(get(TIMELINE_URL).param("id", "1").accept(CONTENT_TYPE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void getTimelineInvalid() throws Exception {
		// Nonexistent users
		this.mockMvc.perform(get(TIMELINE_URL + "/" + 4).accept(CONTENT_TYPE)).andExpect(status().isNotFound());
		this.mockMvc.perform(get(TIMELINE_URL).param("id", "4").accept(CONTENT_TYPE)).andExpect(status().isNotFound());

		// Both id and param provided
		this.mockMvc.perform(get(TIMELINE_URL + "/" + 1).param("id", "1").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Negative id provided
		this.mockMvc.perform(get(TIMELINE_URL + "/" + -5).accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(TIMELINE_URL).param("id", "-5").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());

		// Nan id provided
		this.mockMvc.perform(get(TIMELINE_URL + "/Pikachu").accept(CONTENT_TYPE)).andExpect(status().isBadRequest());
		this.mockMvc.perform(get(TIMELINE_URL).param("id", "Raichu").accept(CONTENT_TYPE))
				.andExpect(status().isBadRequest());
	}
}
