package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import util.MainMemory;

public class User {
	private final long id;
	private String username;
	private Wall wall;
	private Collection<User> followers;

	public User(String username) {
		this(MainMemory.getAvailableId(), username);
	}

	public User(long id, String username) {
		this.id = id;
		this.username = username;
		this.wall = new Wall(id);
		followers = new HashSet<>();
		MainMemory.addUser(this);
	}

	public boolean addFollower(User user) {
		if (user == null || user == this)
			return false;

		return followers.add(user);
	}

	public Collection<Post> getTimeline() {
		return followers.stream().flatMap(user -> user.getWall().getPosts().stream()).sorted()
				.collect(Collectors.toList());
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Wall getWall() {
		return wall;
	}

	public Collection<User> getFollowers() {
		return followers;
	}

	@Override
	public String toString() {
		return username + " - " + id;
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
