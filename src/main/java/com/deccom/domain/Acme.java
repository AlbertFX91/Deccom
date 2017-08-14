package com.deccom.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Acme.
 */
@Document(collection = "acme")
public class Acme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Size(min = 0, max = 225)
    @Field("description")
    private String description;

    @NotNull
    @Field("publication_date")
    private LocalDate publication_date;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Field("rating")
    private Integer rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Acme title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Acme description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public Acme publication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
        return this;
    }

    public void setPublication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public Integer getRating() {
        return rating;
    }

    public Acme rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Acme acme = (Acme) o;
        if (acme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Acme{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", publication_date='" + getPublication_date() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }
}
