package project.android.gbookslisting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * An {@link Adapt} knows how to create a list item layout for each book
 * in the data source {a list of {@link Book} objects}.
 * <p>
 * These list item layouts will be provided to an adapter view like listViwe
 * to be displayed to the user
 */
public class Adapt extends ArrayAdapter<Book> {

    private ArrayList<Book> b;

    /**
     * Constructs a new {@link Adapt} adapter.
     *
     * @param context of the app
     * @param books is the data source of the adapter
     */
    public Adapt (Context context, List<Book> books) {
        super(context, 0, books);
        books = b;
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books
     */
    @Override
    public View getView (int position, View listView, ViewGroup parent) {
        Log.i(Adapt.class.getSimpleName(), "getView of Adapt starts");

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.hits_page, parent, false);
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Find the TextVie ith view ID magnitude
        TextView titleView = listView.findViewById(R.id.title);
        // Display the title of the current book in that TextView
        titleView.setText(currentBook.getBook_title());

        TextView writ = listView.findViewById(R.id.author);
        writ.setText(currentBook.getAuthor());

        // Display the date of the current book in that TextView
        String dateStr = currentBook.getPublishing_year();
        // Find the TextView with view ID date
        TextView dateView = listView.findViewById(R.id.year);
        // Display the publishing year (date) of the current book in that TextView
        dateView.setText(dateStr);

        // Return the list item view that is now showing the appropriate data
        return listView;
    }

}

//        Date dateStr = new Date (String.valueOf(currentBook.getPublishing_year()));
//        dateView.setText(formattedDate);
// Format the date string (i.e. "Mar 3, 1984")
//    String formattedDate = formatDate(dateStr);
//    /**
//     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
//     */
//    private String formatDate(Date dateObject) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
//        return dateFormat.format(dateObject);
//    }