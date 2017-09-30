package com.deccom.domain;

public class Post {

	private Integer userId;
	private Integer id;
	private String title;
	private String body;

	public Post(Integer userId, Integer id, String title, String body) {
		
		super();
		this.userId = userId;
		this.id = id;
		this.title = title;
		this.body = body;
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post{" + "userId=" + getUserId() + ", id='" + getId()
				+ ", title='" + getTitle() + "'" + ", body='" + getBody() + "'"
				+ "}";
	}

}
