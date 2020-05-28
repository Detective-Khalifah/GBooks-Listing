package project.android.gbookslisting;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static project.android.gbookslisting.ResultsActivity.adapter;

public class ParamsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int LOADER_ID = 0;
    private static String LOG_TAG = ParamsActivity.class.getName();
    EditText text;
    String query;
    private LoaderManager loaderManager = getLoaderManager();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(LOG_TAG, "App has launched, Khal!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters);

        // TODO: Fix theming; resize textviews for diff. volume info, move TextViews, restructure layout!
        // TODO: Add parameter boxes as imagined and take results ListView to a diff. page/layout;
        //  consider managing it on same screen - like by hiding parameters after clicking search and showing again if EditText is in scope

        Button query = findViewById(R.id.search_button);
        text = findViewById(R.id.deets_field);


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

            Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) data);
            i.putExtras(bundle);
            startActivity(i);


        }
    }

    @Override
    public void onLoaderReset (Loader loader) {
        adapter.clear();
        adapter = new Adapt(this, new ArrayList<Book>());
    }

}