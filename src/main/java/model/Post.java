package model;

import java.util.Date;

public class Post {
	private long id;
	private String text;
	private Date datePosted;

	public Post(long id, String text, Date datePosted) {
		this.id = id;
		this.text = text;
		this.datePosted = datePosted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
}
