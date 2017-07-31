package model;

import java.util.ArrayList;
import java.util.Collection;

public class Wall {
	private long userId;
	private Collection<Post> posts = new ArrayList<>();

	public Wall(long userID) {
		this.userId = userID;
	}

	public boolean addPost(Post post) {
		if (post == null)
			return false;

		post.setUserId(userId);
		return posts.add(post);
	}

	public boolean createAndAddPost(String text) {
		if (text == null || text.isEmpty())
			return false;

		return addPost(new Post(userId, text, posts.size()));
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Wall: " + posts.size();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userID) {
		this.userId = userID;
	}
}
