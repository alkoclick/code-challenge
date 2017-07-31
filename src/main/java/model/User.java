package model;

import util.MainMemory;

public class User {
	private final long id;
	private String username;
	private Wall wall;

	public User(String username) {
		this(MainMemory.getAvailableId(), username);
	}

	public User(long id, String username) {
		this.id = id;
		this.username = username;
		this.wall = new Wall(id);
		MainMemory.addUser(this);
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

	public Wall getWall() {
		return wall;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}

}
