package project.android.gbookslisting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Book implements Parcelable {

    private String book_title;
    private String author;
    private String publishing_year;
    private String page;
    // for Parcel
    Book book; ArrayList<Book> bArr;


    public Book (String theTitle, String theAuthor, String theYear, String thePage /* More params for Parcel(able)*/) {
        this.book_title = theTitle;
        this.author = theAuthor;
        this.publishing_year = theYear;
        this.page = thePage;
    }

    public Book(Parcel in) {
        this.book_title = in.readString();
        this.author = in.readString();
        this.publishing_year = in.readString();
        this.page = in.readString();
      /**
        // readParcelable for Class Loader
        this.book = in.readParcelable(Book.class.getClassLoader());

        // read list by using Book.CREATOR
        this.bArr = new ArrayList<Book>();
        in.readTypedList(bArr, Book.CREATOR);
       */
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel (Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray (int size) {
            return new Book[size];
        }
    };

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

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel parcel, int flags) {
        parcel.writeString(book_title);
        parcel.writeString(author);
        parcel.writeString(publishing_year);
        parcel.writeString(page);
        /**
        parcel.writeParcelable(book, flags);
        parcel.writeTypedList(bArr); */
    }

}
