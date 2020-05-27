package project.android.gbookslisting;

import java.io.Serializable;
import java.util.Date;

public class Book {

    private String book_title;
    private String author;
    private String publishing_year;
    private String page;


    public Book (String theTitle, String theAuthor, String theYear, String thePage) {
        this.book_title = theTitle;
        this.author = theAuthor;
        this.publishing_year = theYear;
        this.page = thePage;
    }


    public Book setBook_title (String book_title) {
        this.book_title = book_title;
        return this;
    }

    public Book setAuthor (String author) {
        this.author = author;
        return this;
    }

    public Book setPublishing_year (String publishing_year) {
        this.publishing_year = publishing_year;
        return this;
    }

    public Book setPage (String page) {
        this.page = page;
        return this;
    }

    protected String getAuthor () {
        return author;
    }

    protected String getPublishing_year () { return publishing_year; }

    protected String getPage () {
        return page;
    }

    protected String getBook_title () {
        return book_title;
    }
}
