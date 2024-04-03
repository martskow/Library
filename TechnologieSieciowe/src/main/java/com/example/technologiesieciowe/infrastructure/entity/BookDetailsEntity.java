package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_details")
public class BookDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookDetailId")
    private Integer bookDetailId;

    @OneToOne
    @JoinColumn(name = "Id")
    private BookEntity book;

    @Column(name = "Genre")
    private String genre;

    @Column(name = "Summary", length = 1000)
    private String summary;

    @Column(name = "CoverImageURL")
    private String coverImageURL;

    public Integer getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }
}