package control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Wall;

@RestController
public class TimelineController {

	@RequestMapping(method = RequestMethod.GET, value = { "/timeline" })
	public ResponseEntity<Wall> wall(@RequestParam(value = "id", defaultValue = "1") long id) {
		return ResponseEntity.ok().build();
	}
}
