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

import model.User;
import model.Wall;
import util.MainMemory;
import util.Messages;

@RestController
public class WallController {

	private static final int MAX_LENGTH = Messages.getInt("Post.MaxLength");
	public static final String WALL_URI = "/wall";

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET, value = { WALL_URI,
			WALL_URI + "/{idPath}" })
	public ResponseEntity<Wall> getWall(@RequestParam(value = "id", defaultValue = "-1") long idParam,
			@PathVariable(required = false) Long idPath) {
		long id = idPath != null && idPath > 0 ? idPath : idParam;
		if (id < 1 || (idPath != null && idParam > 0))
			return ResponseEntity.badRequest().build();
		if (!MainMemory.getUsers().keySet().contains(id))
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(MainMemory.getUsers().get(id).getWall());

	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = { WALL_URI,
			WALL_URI + "/{idPath}" })
	ResponseEntity<?> postToWall(@RequestParam(value = "id", defaultValue = "-1") long idParam,
			@PathVariable(required = false) Long idPath, @RequestBody(required = false) String body) {
		ResponseEntity<Object> response;
		long id = idPath != null && idPath > 0 ? idPath : idParam;
		if (id < 1 || (idPath != null && idParam > 0))
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else if (body == null || body.isEmpty())
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else if (body.length() > MAX_LENGTH)
			response = new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
		else {
			if (!MainMemory.getUsers().keySet().contains(id))
				new User(id);
			response = ResponseEntity.ok(MainMemory.getUsers().get(id).getWall().createAndAddPost(body));
		}

		return response;

	}
}
