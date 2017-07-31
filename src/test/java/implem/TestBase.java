package implem;

import model.User;
import util.MainMemory;

public class TestBase {

	public static void createSampleUsers() {
		new User(1, "Alex");
		new User(2, "Mateusz");
		new User(3, "Guilherme");
	}

	public static void createSamplePosts() {
		MainMemory.getUsers().values().forEach(user -> user.getWall().createAndAddPost("Howdy world!"));
	}

	public static void createFollows() {
		// Let's make everyone follow everyone else
		// This will include themselves, but addFollows should make sure that's
		// not gonna happen
		MainMemory.getUsers().values().forEach(user -> {
			MainMemory.getUsers().values().forEach(user2 -> user.addFollower(user2));
		});
	}
}
