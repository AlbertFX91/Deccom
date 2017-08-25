package com.deccom.domain;

public class Book {

	private Integer id;
	private String title;
	private String description;

	public Book(Integer id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Book{" + "id=" + getId() + ", title='" + getTitle() + "'"
				+ ", description='" + getDescription() + "'" + "}";
	}

}
