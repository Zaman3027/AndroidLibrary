package com.example.libararymanager.Model;

public class BookModel {
    String BookName,Author,Publisher,Genre,Rating,quantity,imageLink, bookBescription;

    public BookModel() {
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBookBescription() {
        return bookBescription;
    }

    public void setBookBescription(String bookBescription) {
        this.bookBescription = bookBescription;
    }

    public BookModel(String bookName, String author, String publisher, String genre, String rating, String quantity, String imageLink, String bookBescription) {
        BookName = bookName;
        Author = author;
        Publisher = publisher;
        Genre = genre;
        Rating = rating;
        this.quantity = quantity;
        this.imageLink = imageLink;
        this.bookBescription = bookBescription;
    }
}
