package project.android.gbookslisting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    static Adapt adapter;
    static TextView emptyResult;
    ListView bookEntries;
    String LOG_TAG = ResultsActivity.class.getName();
    List<Book> books = new ArrayList<Book>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hits_page);

        Bundle bundle = getIntent().getExtras();
        books = bundle.getParcelableArrayList("data");

        emptyResult = findViewById(R.id.matches_nill);
        emptyResult.setText(R.string.matches0);

        if (!books.isEmpty()) {
            emptyResult.setVisibility(View.GONE);

            // Create a new adapter that takes a rich (or otherwise empty) list of books as input
            adapter = new Adapt(this, new ArrayList<Book>());

            // Get the list of books from {@link Search}
            bookEntries = findViewById(R.id.catalog);

            bookEntries.setAdapter(adapter);

            bookEntries.setEmptyView(emptyResult);

            bookEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current book that was clicked on
                    Book currentBook = adapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri bookUri = Uri.parse(currentBook.getPage());

                    // Create a new intent to view the book URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

            adapter.clear();

            adapter.addAll(books);
        } else {
            emptyResult.setVisibility(View.VISIBLE);
            emptyResult.setText(R.string.matches0);
        }

    }
}