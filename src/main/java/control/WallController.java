package control;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Wall;

@RestController
public class WallController {
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(method = RequestMethod.GET, value = { "/wall" })
	public Wall wall(@RequestParam(value = "id", defaultValue = "1") long id) {
		return new Wall();
	}
}
