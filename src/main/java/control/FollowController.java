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
			FOLLOWS_URL, FOLLOWS_URL + "/{idFollowerPath}", FOLLOWS_URL + "/{idFollowerPath}/{idFolloweePath}" })
	ResponseEntity<?> postFollows(@RequestParam(value = "idFollower", defaultValue = "-1") long idFollowerParam,
			@RequestParam(value = "idFollowee", defaultValue = "-1") long idFolloweeParam,
			@PathVariable(required = false) Long idFollowerPath, @PathVariable(required = false) Long idFolloweePath,
			@RequestBody(required = false) String body) {

		long idFollower = idFollowerPath != null && idFollowerPath > 0 ? idFollowerPath : idFollowerParam;
		long idFollowee = idFolloweePath != null && idFolloweePath > 0 ? idFolloweePath : idFolloweeParam;
		ResponseEntity<Object> response;

		System.out.println(idFollower + " " + idFollowee);
		if ((idFollower < 1 || (idFollowerPath != null && idFollowerParam > 0))
				|| (idFollowee < 1 || (idFolloweePath != null && idFolloweeParam > 0)))
			return ResponseEntity.badRequest().build();
		else if (!MainMemory.getUsers().keySet().contains(idFollower)
				|| !MainMemory.getUsers().keySet().contains(idFollowee))
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			response = ResponseEntity
					.ok(MainMemory.getUsers().get(idFollower).addFollowed(MainMemory.getUsers().get(idFollowee)));

		return response;
	}
}
