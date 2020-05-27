package project.android.gbookslisting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Search {

    private static final String GET = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = /*"key=*/"AIzaSyCFHqZ7MM6vAkZ916SAjc_AP6DrE_5n-sU";

    /**
     * @param requestURL an initial, unready search query
     */
    protected static List<Book> lookUpVolumes (String requestURL) {
//        Log.i(Search.class.getName(), "lookUpVolumes in action.");

        // RESTful API prefix
        StringBuilder generateQuery = new StringBuilder(GET);

        // Check if character at specific position is whitespace and replace it with '+'/"+"
        generateQuery.append(requestURL.replaceAll("[\\s|\\u00A0]+", "+")) /* limit number of items/results to return here .append("&limit=10") */;

        // Append API key to query
//        generateQuery.append("&").append(API_KEY);

        Log.i(Search.class.getName(), "Generated Query: " + generateQuery);

        // Create URL obj
        URL url = createURL(generateQuery.toString());

        // Perform HTTP request to the URL and receive a JSON response back
        String JSONResponse = null;

        try {
            JSONResponse = makeHTTPRequest(url);
        } catch (IOException e) {

        }

        // Generate a list of books from fetched GeoJSON
        List<Book> searchResult = extractJSONData(JSONResponse);
//        Log.i(Search.class.getName(), "SearchResult List is made of: " + searchResult);
        return searchResult;
    }

    /**
     * @param stringURL
     * @return
     */
    private static URL createURL (String stringURL) {
        Log.i(Search.class.getName(), "createURL");
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException mURLE) {

        }
        return url;
    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    private static String makeHTTPRequest (URL url) throws IOException {
        String JSONResponse = "";

        // If the URL is null, then return early
        if (url == null) {
            return JSONResponse;
        }

        HttpURLConnection urlConn = null;
        InputStream inputStream = null;

        try {
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setReadTimeout(36000 /* milliseconds */);
            urlConn.setConnectTimeout(600 /* milliseconds */);
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("key", API_KEY);
            urlConn.connect();

            // If the request was successful (response code 200), then read the input stream and parse the response.
            if (urlConn.getResponseCode() == 200) {
                Log.i(Search.class.getName(), "from makeHTTPRequest: Response code 200");
                inputStream = urlConn.getInputStream();
//                Log.i(Search.class.getName(), "inputStream:: " + inputStream);
                JSONResponse = readFromStream(inputStream);
//                Log.i(Search.class.getName(), "JSONResponse:: " + JSONResponse);
            } else {
                // Toast.makeText(this, "Please enable network connection", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {

        } finally {
            if (urlConn != null)
                urlConn.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return JSONResponse;
    }

    /**
     * @param inStream
     * @return
     * @throws IOException
     */
    private static String readFromStream (InputStream inStream) throws IOException {
        StringBuilder myList = new StringBuilder();
        if (inStream != null) {
            InputStreamReader inStreamDecode = new InputStreamReader(inStream);
            BufferedReader reader = new BufferedReader(inStreamDecode);

            String line = reader.readLine();
            while (line != null) {
                myList.append(line);
                line = reader.readLine();
            }
        }
        return myList.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Book> extractJSONData (String booksJSON) {

        // If the JSON String is empty or null, then return early.
        if (TextUtils.isEmpty(booksJSON))
            return null;

        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Fix query url to be API-keyless
            // TODO: Generate thumbnail image; I saw a hint on page I visited on Opera; parse the low-res img, set it to ImageView.
            // TODO: Make app capable of accepting hundreds of results;
            //  has to be later on, since I can't let user wait eternally for results and haven't figured out how to get more than 10 items.
            //  For now, stick to avoiding crashes when some detail(s)'(s)/(are)  missing.
            // build up a list of Book objects with the corresponding data.

            // Create a JSON object from the SAMPLE_JSON_RESPONSE string
            JSONObject rootJSONObj = new JSONObject(booksJSON);

            // Extract the JSONArray associated with the key called "items"
            // which represents a list of features (or books).
            JSONArray bookArray = rootJSONObj.getJSONArray("items");

            // For each book in the bookArray, create an {@link book object
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBookObj = bookArray.getJSONObject(i);
//                Log.i(Search.class.getName(), "currentBookObj:: " + currentBookObj);

                JSONObject currentVolInfo = currentBookObj.getJSONObject("volumeInfo");
//                Log.i(Search.class.getName(), "currentVolInfo:: " + currentVolInfo);
                // TODO: Get thumbnail from above JSONObj

                /* Extract title*/
                String title = currentVolInfo.getString("title");
                Log.i(Search.class.getName(), "title:: " + title);

                /* Extract author(s)*/
                JSONArray authorsJSON = currentVolInfo.getJSONArray("authors");
//                String[] authors = {String.valueOf(currentVolInfo.getJSONArray("authors"))};

                StringBuilder maybeMore = new StringBuilder();
                String writ;
                int k = 0;
                while (k < authorsJSON.length()) {
                    if (k == (authorsJSON.length() - 1)) {
                        maybeMore.append(authorsJSON.optString(k));
                        break;
                    } else
                        maybeMore.append(authorsJSON.optString(k)).append(", ");
                    k++;
                }
                writ = String.valueOf(maybeMore);

                if (authorsJSON.length() == 1) {
                    writ = authorsJSON.optString(0);
                } else if (authorsJSON.length() < 1) {
                    writ = "Unidentified.";
                }

                Log.i(Search.class.getName(), "authorArr:: " + writ);

                /* Extract year*/
                String year = currentVolInfo.getString("publishedDate").substring(0, 4);
                Log.i(Search.class.getName(), "year:: " + year);

                /* Extract volume/book ID - a unique identifier for the current book, which can be used to uniquely open book info online */
//                String web_page = currentBookObj.getString("selfLink");
                String web_page = currentVolInfo.getString("previewLink");
                Log.i(Search.class.getName(), "web_page:: " + web_page);


//                Log.i(Search.class.getName(), "book:: " + book);
                books.add(new Book(title, writ, year, web_page));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(Search.class.getName(), "Problem parsing the JSON results_page", e);
        }

        // Return the list (polymorphed ArrayList) of books
        return books;
    }
}

// Check if character at specific position is whitespace and replace it with '+'/"+"
//        for (int i = 0; i < requestURL.length(); i++){
//            if (Character.isWhitespace(requestURL.charAt(i))){
//                requestURL.charAt(i) = '+';
//            }
//        }
//        generateQuery.append(requestURL);

//                Date yyyy = new Date(year);
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//                year = formatter.format(yyyy);

//                writ = writ.replaceAll("\\W", "");
//                writ = writ.replaceAll("[^...]","");
//                writ = writ.replace("^","").replace("$","");

//                String[] authors = {String.valueOf(authorsJSON)};
//                StringBuilder maybeMore = new StringBuilder();
//                String quote = "\"";
//                String writ;
//                if (authorsJSON != null && authors.length > 1) {
//                    for (int k = 0; k < authors.length; k++) {
//                        if (authors.length == (k-1))
//                            maybeMore.append(authors[k]);
//                        else
//                            maybeMore.append(authors[k] + " ");
//                    }
//                    writ = String.valueOf(maybeMore);
//                } else {
//                    writ = authors[0];
//                }

//                String quote = "\"";
//            writ = writ.replaceAll(quote, "").replace((String.valueOf(writ.charAt(0))), "").replace(String.valueOf(writ.charAt(writ.length() - 1)), "");