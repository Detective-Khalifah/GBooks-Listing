package project.android.gbookslisting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * An {@link Adapt} knows how to create a list item layout for each earthquake
 * in the data source {a list of {@link Book} objects}.
 *
 * These list item layouts will be provided to an adapter view like listViwe
 * to be displayed to the user
 */
public class Adapt extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link Adapt} adapter.
     * @param context of the app
     * @param books is the earthquake, which is the data source of the adapter
     */
    public Adapt(Context context, List<Book> books){
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes
     */
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        Log.i(Adapt.class.getSimpleName(), "getView of Adapt starts");

        View listView = convertView;

        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.volume, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Book currentBook = getItem(position);

        // Find the TextVie ith view ID magnitude
        TextView titleView = (TextView) listView.findViewById(R.id.title);
        // Display the title of the current book in that TextView
        titleView.setText(currentBook.getBook_title());

        TextView writ = (TextView) listView.findViewById(R.id.author);
        writ.setText(currentBook.getAuthor());

        // Display the date of the current book in that TextView
        String dateStr = currentBook.getPublishing_year();
        // Find the TextView with view ID date
        TextView dateView = (TextView) listView.findViewById(R.id.year);
        // Display the date of the current earthquake in that TextView
        dateView.setText(dateStr);

        // Return the list item view that is now showing the appropriate data
        return listView;
    }

}

// Create a new Date object from the time in milliseconds of the earthquake
//        Date dateStr = new Date (String.valueOf(currentBook.getPublishing_year()));
// Display the date of the current earthquake in that TextView
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

/** get url of crrent earthquake for EarthQuake Activity to imbue into ListView */
//        current_page = currentEarthquake.getPage();
//        TextView currentPage = (TextView) listView.findViewById(R.id.url);
//        EarthquakeActivity.url = current_page;
//        currentPage.setText(current_page);