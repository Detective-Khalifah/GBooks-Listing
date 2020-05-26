package project.android.gbookslisting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity implements Serializable {

    static Adapt adapter;
    static TextView emptyResult;
    ListView bookEntries;
    String LOG_TAG = ResultsActivity.class.getName();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hits_page);

        Intent i = getIntent();
        ArrayList<Book> books = (ArrayList<Book>) i.getSerializableExtra("data");
        Log.i(LOG_TAG, "books::" + books);

        emptyResult = findViewById(R.id.matches_nill);
        Log.i(LOG_TAG, "emptyResult TextView:: " + emptyResult);
        emptyResult.setText(R.string.matches0);

        if (!books.isEmpty()) {
            emptyResult.setVisibility(View.GONE);

            // Create a new adapter that takes a rich (or otherwise empty) list of books as input
            adapter = new Adapt(this, new ArrayList<Book>());
            Log.i(LOG_TAG, "adapter is 1B::: " + adapter);

            // Get the list of books from {@link Search}
            bookEntries = findViewById(R.id.catalog);
            Log.i(LOG_TAG, "bookEntries ListView 1A::: " + bookEntries);

            bookEntries.setAdapter(adapter);
            Log.i(LOG_TAG, "Adaper set on ListView:: " + bookEntries);

            bookEntries.setEmptyView(emptyResult);

            bookEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView<?> adapterView, View view, int position, long l) {
                    Log.i(LOG_TAG, "bookEntries onItemClickListener");
                    // Find the current earthquake that was clicked on
                    Book currentBook = adapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri bookUri = Uri.parse(currentBook.getPage());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

//            adapter.clear();
//        Log.i(LOG_TAG, "adapter cleared.");

            adapter.addAll(books);
            Log.i(LOG_TAG, "adapter.addAll(data) executed");
        } else {
            emptyResult.setVisibility(View.VISIBLE);
            emptyResult.setText(R.string.matches0);
        }

    }
}

//        adapter = new Adapt(this, R.layout.hits_page, books);