package com.example.sqlite_test;

public class Book {
    private long id;
    private String title;
    private String author;
    private String comment;

    public Book(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.comment = null;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
