package model;

import java.time.Instant;
import java.util.Date;

public class Post {
	// Completed by us when posted
	private String id;
	private Date datePosted;
	private long userId;

	private String text;

	public Post(long userId, String text, long id) {
		this.userId = userId;
		this.text = text;
		this.id = userId + "_" + id;
		this.datePosted = Date.from(Instant.now());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
