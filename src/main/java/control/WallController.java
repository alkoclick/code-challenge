package control;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Wall;
import util.MainMemory;

@RestController
public class WallController {

	private static final int MAX_LENGTH = 140;

	@RequestMapping(method = RequestMethod.GET, value = { "/wall", "/wall/{idPath}" })
	public ResponseEntity<Wall> getWall(@RequestParam(value = "id", defaultValue = "-1") long idParam,
			@PathVariable Long idPath) {
		long id = idPath != -1 ? idPath : idParam;
		if (id == -1)
			return ResponseEntity.badRequest().build();
		if (!MainMemory.getUsers().keySet().contains(id))
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(MainMemory.getUsers().get(id).getWall());

	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = { "/wall",
			"/wall/{idPath}" })
	ResponseEntity<?> postToWall(@RequestParam(value = "id", defaultValue = "-1") long idParam,
			@PathVariable Long idPath, @RequestBody(required = false) String body) {
		long id = idPath != -1 ? idPath : idParam;

		ResponseEntity<Object> response;
		if (id == -1)
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else if (!MainMemory.getUsers().keySet().contains(id))
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else if (body == null || body.isEmpty())
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else if (body.length() > MAX_LENGTH)
			response = new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
		else
			response = ResponseEntity.ok(MainMemory.getUsers().get(id).getWall().createAndAddPost(body));

		System.out.println("Content type: " + response.getHeaders().getContentType());

		return response;

	}
}
