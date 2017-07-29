package control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Post;
import model.Wall;

@RestController
public class WallController {

	@RequestMapping(method = RequestMethod.GET, value = { "/wall" })
	public ResponseEntity<Wall> wall(@RequestParam(value = "id", defaultValue = "1") long id) {
		return ResponseEntity.ok(new Wall());
	}

	@RequestMapping(method = RequestMethod.POST, value = { "/wall" })
	ResponseEntity<?> wall(@RequestParam(value = "id", defaultValue = "1") long id, @RequestBody Post post) {

		ResponseEntity.badRequest().build();
		return ResponseEntity.ok(null);
	}
}
