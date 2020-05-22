package project.android.gbookslisting;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v4.content.AsyncTaskLoader;
//import android.support.v7.app.AppCompatActivity;

public class ParamsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static String LOG_TAG = ParamsActivity.class.getName();
    private LoaderManager loaderManager = getLoaderManager();
    private static final int LOADER_ID = 0;
    Adapt adapter;
    EditText text;
    ListView bookEntries;
    TextView emptyResult;
    String query;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(LOG_TAG, "App has launched, Khal!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters);

        // TODO: Fix theming; resize textviews for diff. volume info, move textviews, restructure layout!
        // TODO: Add parameter boxes as imagined and take results ListView to a diff. page/layout;
        //  consider managing it on same screen - like by hiding parameters after clicking search and showing again if EditText is in scope


        Button query = (Button) findViewById(R.id.search_button);
        text = (EditText) findViewById(R.id.deets_field);
        emptyResult = (TextView) findViewById(R.id.matches_nill);

        // Get the list of books from {@link Search}
        bookEntries = (ListView) findViewById(R.id.book_entries);
//        Log.i(LOG_TAG, "bookEntries ListView 1A::: " + bookEntries);

        // Create a new adapter that takes an empty list of earthquakes as input
        adapter = new Adapt(this, new ArrayList<Book>());
//            adapter = new Adapt(ParamsActivity.this, data);
//            Log.i(LOG_TAG, "adapter is 1B::: " + adapter);





        bookEntries.setAdapter(adapter);
        Log.i(LOG_TAG, "Adaper set on ListView:: " + bookEntries);

        bookEntries.setEmptyView(emptyResult);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                    Log.i(LOG_TAG, "Seach button clicked:: ");
                if (text.getText().toString().length() > 0) {
                    //TODO: Run a network connectivity test here and set color of button to red or green, depending on status; also set it to orange when searching.
                    // TODO: Consider taking out keyboard after search is clicked.
                    // TODO: Find out how to make search button accept new search(es) in stead of calling onFinished() recursively.
                    loaderManager.initLoader(LOADER_ID, null, ParamsActivity.this);
                    Log.i(LOG_TAG, "LoaderManager initialised called::");
                } else if (text.getText().length() < 1){
                    text.setHint("Please enter book title/details");
                    text.setHintTextColor(Color.RED);
                    Log.e(LOG_TAG, "Title and/or details might not have been entered. Contingency plan executed.");
                }
            }
        });

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

    }

    @Override
    public Loader<List<Book>> onCreateLoader (int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader() called");
        query = text.getText().toString();
        return new BookLoader(this, query);
    }

    @Override
    public void onLoadFinished (Loader<List<Book>> loader, List<Book> data) {
        Log.i(LOG_TAG, "onLoadFinished() called");

        adapter.clear();
        Log.i(LOG_TAG, "adapter cleared.");

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's dataset. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            emptyResult.setVisibility(View.GONE);
//            emptyResult.setText("Books found.");
            Log.i(LOG_TAG, "Data not empty in onPostExecute's check");

            adapter.addAll(data);
            Log.i(LOG_TAG, "adapter.addAll(data) executed");
//                TextView tv = findViewById(R.id.check);
//                tv.setText(data.toString());
        } else {
            emptyResult.setText(R.string.matches0);
        }
    }

    @Override
    public void onLoaderReset (Loader loader) {
        Log.i(LOG_TAG, "onLoaderReset() called");
        adapter = new Adapt(this, new ArrayList<Book>());
        adapter.clear();
    }

}
// try {
//         text = (EditText) findViewById(R.id.deets_field);
////                    Log.i(LOG_TAG, "EditText in Java:: " + text);
//         query = text.getText().toString();
//         getSupportLoaderManager.initLoader(0, null, ParamsActivity.this);
//
//         } catch (NullPointerException npe) {
//         text.setHint("Please enter book title/details");
//         text.setHintTextColor(Color.RED);
//         Log.e(LOG_TAG, "Title and/or details might not have been entered. Contingency plan executed.");
//         }

//    String[] ti = {text.getText().toString()};
//                    Log.i(LOG_TAG, "EditText extracted using ti Arr:" + Arrays.toString(ti));
//                    RunSearch search = new RunSearch();
//                    Log.i(LOG_TAG, "Search Task obj created");
//                    search.execute(ti);
//Log.i(LOG_TAG, "Search Task obj has executed, with the following" + Arrays.toString(ti));
//    private class RunSearch extends AsyncTask<String, Void, List<Book>> {
//
//        @Override
//        protected List<Book> doInBackground (String... urls) /* throws NullPointerException */ {
//            Log.i(LOG_TAG, "This is doInBacground. I received: " + urls[0]);
//            // Don't perform the request if there are no URLs, or the first URL is null.
//            if (urls.length < 1 || urls[0] == null) {
//                Log.i(LOG_TAG, "Conditional check finds null");
//                return null;
//            } else {
//                    List<Book> result = Search.lookUpVolumes(urls[0]);
//                    Log.i(LOG_TAG, "result List data: " + result);
//                    return result;
//            }
//        }

//        @Override
//        protected void onPostExecute (List<Book> data) /* throws NullPointerException */ {
//            Log.i(LOG_TAG, "This is onPostExecute. doInBackground() returned: "+ data);
//
//            // Set the adapter on the {@link ListView} so the list can be populated in the user interface
//            bookEntries.setAdapter(adapter);
//            Log.i(LOG_TAG, "adapter set on Listiew");
//            adapter.clear();
//            Log.i(LOG_TAG, "adapter cleared.");
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's dataset. This will trigger the ListView to update.
//            if (data != null & !data.isEmpty()) {
//                emptyResult.setText("Books found.");
//                Log.i(LOG_TAG, "Data not empty in onPostExecute's check");
//
//                adapter.addAll(data);
//                Log.i(LOG_TAG, "adapter.addAll(data) executed");
////                TextView tv = findViewById(R.id.check);
////                tv.setText(data.toString());
//            } else {
//                emptyResult.setText(R.string.matches0);
//            }
//
//        }
//    }