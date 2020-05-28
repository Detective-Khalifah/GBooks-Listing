package project.android.gbookslisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

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
        if (query.length() < 1) {
            return null;
        } else {

            return Search.lookUpVolumes(query);
        }
    }
}
