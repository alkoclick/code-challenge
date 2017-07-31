package control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import util.MainMemory;

@RestController
public class TimelineController {
	public static final String TIMELINE_URL = "/timeline";

	@RequestMapping(method = RequestMethod.GET, value = { TIMELINE_URL, TIMELINE_URL + "/{idPath}" })
	public ResponseEntity<Object> wall(@RequestParam(value = "id", defaultValue = "-1") long idParam,
			@PathVariable(required = false) Long idPath) {
		long id = idPath != null && idPath > 0 ? idPath : idParam;
		if (id < 1 || (idPath != null && idParam > 0))
			return ResponseEntity.badRequest().build();
		else if (!MainMemory.getUsers().keySet().contains(id))
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(MainMemory.getUsers().get(id).getTimeline());
	}
}
