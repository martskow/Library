package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books", schema = "library")
public class BookEntity {
    @Id@GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<LoanEntity> loans;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;

    @Basic
    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;
    @Basic
    @Column(name = "title", nullable = false)
    private String title;
    @Basic
    @Column(name = "author", nullable = false)
    private String author;
    @Basic
    @Column(name = "publisher")
    private String publisher;
    @Basic
    @Column(name = "publish_year")
    private String publishYear;
    @Basic
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
