package project.android.gbookslisting;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ParamsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int LOADER_ID = 0;
    static Adapt adapter;
    private static String LOG_TAG = ParamsActivity.class.getName();
    String query;
    private static TextView emptyResult;
    private EditText text;
    private ListView bookEntries;
    private LoaderManager loaderManager = getLoaderManager();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(LOG_TAG, "App has launched, Khal!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters);

        // TODO: Fix theming; resize textviews for diff. volume info, move TextViews, restructure layout!
        // TODO: Add parameter boxes as imagined and take results ListView to a diff. page/layout;
        //  consider managing it on same screen - like by hiding parameters after clicking search and showing again if EditText is in scope

        Button query = (Button) findViewById(R.id.search_button);
        text = (EditText) findViewById(R.id.deets_field);
        emptyResult = (TextView) findViewById(R.id.matches_nill);

        // Create a new adapter that takes a rich (or otherwise empty) list of books as input
        adapter = new Adapt(this, new ArrayList<Book>());

        // Get the list of books from {@link Search}
        bookEntries = (ListView) findViewById(R.id.catalog);
        bookEntries.setAdapter(adapter);
        bookEntries.setEmptyView(emptyResult);

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (text.getText().toString().length() > 0) {
                    //TODO: Run a network connectivity test here and set color of button to red or green, depending on status; also set it to orange when searching.
                    // TODO: Consider taking out keyboard after search is clicked.
                    // TODO: Find out how to make search button accept new search(es) in stead of calling onFinished() recursively. ----- Fixed when app was using a SecondActivity
                    loaderManager.restartLoader(LOADER_ID, null, ParamsActivity.this);
                } else if (text.getText().length() < 1) {
                    text.setHint("Please enter book title/details");
                    text.setHintTextColor(Color.RED);
                }
            }
        });


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

    }


    public void showEmptytView () {
        emptyResult.setVisibility(View.VISIBLE);
        emptyResult.setText(R.string.matches0);
    }

    private void hideEmptyTextView () {
        emptyResult.setVisibility(View.GONE);
        emptyResult.setText(R.string.matches0);
    }

    @Override
    public Loader<List<Book>> onCreateLoader (int i, Bundle bundle) {
        query = text.getText().toString();
        return new BookLoader(this, query);
    }

    @Override
    public void onLoadFinished (Loader<List<Book>> loader, List<Book> data) {
        // If there is a valid list of {@link Book}s, then add them to the adapter's dataset. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Books found. Wait a moment for the list.", Toast.LENGTH_SHORT).show();

            hideEmptyTextView();

            adapter.clear();
            adapter.addAll(data);
        } else {
            showEmptytView();
        }
    }

    @Override
    public void onLoaderReset (Loader loader) {
        adapter.clear();
        adapter = new Adapt(this, new ArrayList<Book>());
    }

}