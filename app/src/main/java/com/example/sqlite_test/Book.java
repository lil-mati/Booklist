package com.example.sqlite_test;

public class Book {
    private long id;
    private String title;
    private String author;
    private String comment;
    private String status;

    public Book(long id, String title, String author, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.comment = null;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
