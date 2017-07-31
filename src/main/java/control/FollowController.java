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

import util.MainMemory;

@RestController
public class FollowController {
	public static final String FOLLOWS_URL = "/follow";

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = {
			FOLLOWS_URL, FOLLOWS_URL + "/{idFollowerPath}/{idFolloweePath}" })
	ResponseEntity<?> postFollows(@RequestParam(value = "idFollower", defaultValue = "-1") long idFollowerParam,
			@RequestParam(value = "idFollowee", defaultValue = "-1") long idFolloweeParam,
			@PathVariable(required = false) Long idFollowerPath, @PathVariable(required = false) Long idFolloweePath,
			@RequestBody(required = false) String body) {

		long idFollower = -1;
		long idFollowee = -1;
		ResponseEntity<Object> response;

		try {
			idFollower = idFollowerParam > 0 ? idFollowerParam : idFollowerPath;
			idFollowee = idFolloweeParam > 0 ? idFolloweeParam : idFolloweePath;
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (idFollower < 0 || idFollowee < 0)
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else if (!MainMemory.getUsers().keySet().contains(idFollower)
				|| !MainMemory.getUsers().keySet().contains(idFollowee))
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			response = ResponseEntity
					.ok(MainMemory.getUsers().get(idFollowee).addFollower(MainMemory.getUsers().get(idFollower)));

		return response;

	}
}
