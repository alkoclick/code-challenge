package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import util.MainMemory;

public class User {
	private final long id;
	private Wall wall;
	private Collection<User> followed;

	public User(String username) {
		this(MainMemory.getAvailableId());
	}

	public User(long id) {
		this.id = id;
		this.wall = new Wall(id);
		followed = new HashSet<>();
		MainMemory.addUser(this);
	}

	public boolean addFollowed(User user) {
		if (user == null || user == this)
			return false;

		return followed.add(user);
	}

	public Collection<Post> getTimeline() {
		return followed.stream().flatMap(user -> user.getWall().getPosts().stream()).sorted()
				.collect(Collectors.toList());
	}

	public long getId() {
		return id;
	}

	public Wall getWall() {
		return wall;
	}

	public Collection<User> getFollowed() {
		return followed;
	}

	@Override
	public String toString() {
		return "User - " + id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other == null || !(other.getClass() == this.getClass()))
			return false;
		User otherUser = (User) other;
		return this.id == otherUser.id;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}

}
