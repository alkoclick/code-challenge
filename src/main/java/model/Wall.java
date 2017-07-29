package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Wall {
	private User owner;
	private Collection<Post> posts;

	public Wall() {
		User user = new User();
		user.setId(1);
		user.setUsername("Alex");
		posts = new ArrayList<>();
		posts.add(new Post(1, "Testorz", Date.from(Instant.now())));
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}
}
