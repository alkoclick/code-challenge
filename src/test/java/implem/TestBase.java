package implem;

import model.User;
import util.MainMemory;
import util.Messages;

public class TestBase {
	private static final int MAX_LENGTH = Messages.getInt("Post.MaxLength");

	public static void createSampleUsers() {
		new User(1);
		new User(2);
		new User(3);
	}

	public static void createSamplePosts() {
		MainMemory.getUsers().values().forEach(user -> user.getWall().createAndAddPost("Howdy world!"));
	}

	public static void createFollows() {
		// Let's make everyone follow everyone else
		// This will include themselves, but addFollows should make sure that's
		// not gonna happen
		MainMemory.getUsers().values().forEach(user -> {
			MainMemory.getUsers().values().forEach(user2 -> user.addFollowed(user2));
		});
	}

	public static String createOversizedText() {
		String shouldBeOversized = "";
		while (shouldBeOversized.length() < MAX_LENGTH) {
			shouldBeOversized += "Pika pika";
		}
		return shouldBeOversized;
	}
}
