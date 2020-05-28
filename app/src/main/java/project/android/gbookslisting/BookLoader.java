package project.android.gbookslisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.annotation.Nullable;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String query;

    protected BookLoader (Context context, String theQuery) {
        super(context);
        this.query = theQuery;
    }

    @Override
    protected void onStartLoading () {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground () {
        Log.i(BookLoader.class.getName(), "This is loadInBacground. I received: " + query);
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (query.length() < 1) {
            return null;
        } else {
            List<Book> result = Search.lookUpVolumes(query);
            return result;
        }
    }
}
