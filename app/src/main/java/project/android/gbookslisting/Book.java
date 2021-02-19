package project.android.gbookslisting;

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
