package project.android.gbookslisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link Adapt} knows how to create a list item layout for each book
 * in the data source {a list of {@link Book} objects}.
 * <p>
 * These list item layouts will be provided to an adapter view like listView
 * to be displayed to the user
 */
public class Adapt extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link Adapt} adapter.
     *
     * @param context of the app
     * @param books is the data source of the adapter
     */
    public Adapt (Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books
     */
    @Override
    public View getView (int position, View listView, ViewGroup parent) {

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.hits_page, parent, false);
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Find right views and scribe appropriate data to each.
        TextView titleView = listView.findViewById(R.id.title);
        titleView.setText(currentBook.getBook_title());

        TextView writ = listView.findViewById(R.id.author);
        writ.setText(currentBook.getAuthor());

        TextView dateView = listView.findViewById(R.id.year);
        dateView.setText(currentBook.getPublishing_year());

        // Return the list item view that is now showing the appropriate data
        return listView;
    }

}
// TODO: Should there be need to make date appear in a peculiar style, use these to format it. Definitely going to need more. Shouldn't be a case if I fix up Headli9es correctly.

// A       Date dateStr = new Date (String.valueOf(currentBook.getPublishing_year()));
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

// B from Search.java
//                Date yyyy = new Date(year);
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//                year = formatter.format(yyyy);