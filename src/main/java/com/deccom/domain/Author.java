package com.deccom.domain;

import java.time.LocalDate;

public class Author {
	
	private Integer idAuthor;
	private String name;
	private Integer age;
	private LocalDate debut;
	private Double avgBooks;
	
	
	public Integer getIdAuthor() {
		return idAuthor;
	}
	public void setIdAuthor(Integer idAuthor) {
		this.idAuthor = idAuthor;
	}
	public Author IdAuthor(Integer idAuthor) {
		this.idAuthor = idAuthor;
		return this;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Author name(String name) {
		this.name = name;
		return this;
	}
	
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Author age(Integer age) {
		this.age = age;
		return this;
	}
	
	public LocalDate getDebut() {
		return debut;
	}
	public void setDebut(LocalDate debut) {
		this.debut = debut;
	}
	public Author debut(LocalDate debut) {
		this.debut = debut;
		return this;
	}
	
	public Double getAvgBooks() {
		return avgBooks;
	}
	public void setAvgBooks(Double avgBooks) {
		this.avgBooks = avgBooks;
	}
	public Author avgBooks(Double avgBooks) {
		this.avgBooks = avgBooks;
		return this;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAuthor == null) ? 0 : idAuthor.hashCode());
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
		Author other = (Author) obj;
		if (idAuthor == null) {
			if (other.idAuthor != null)
				return false;
		} else if (!idAuthor.equals(other.idAuthor))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Author {idAuthor='" + idAuthor 
				+ "', name='" + name 
				+ "', age='" + age 
				+ "', avgBooks='" + avgBooks
				+ "', debut='" + debut + "'}";
	}	
}
