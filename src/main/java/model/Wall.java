package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Wall {
	private User user;
	private static Collection<Post> posts = new ArrayList<>();

	public Wall() {
		user = new User();
		user.setId(1);
		user.setUsername("Alex");
		posts.add(new Post(1, "Testorz", Date.from(Instant.now())));
	}

	public User getUser() {
		return user;
	}

	public void setUser(User owner) {
		this.user = owner;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return user.toString() + posts.size();
	}
}
