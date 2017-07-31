package util;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import model.User;

public class MainMemory {
	private static Map<Long, User> users = new ConcurrentHashMap<>();
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	public static Map<Long, User> getUsers() {
		return users;
	}

	/**
	 * Convenience method for replacing .getUsers.put(...) If the user ID
	 * already exists, they will be replaced
	 * 
	 * @param user
	 */
	public static void addUser(User user) {
		users.put(user.getId(), user);
	}

	public static Random getRandom() {
		return RANDOM;
	}

	/**
	 * Returns a currently unused user ID
	 * 
	 * @return
	 */
	public static long getAvailableId() {
		long id;
		do {
			id = MainMemory.getRandom().nextLong();
		} while (users.keySet().contains(id));
		return id;
	}
}
