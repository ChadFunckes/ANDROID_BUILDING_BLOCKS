package ismgapps.jsonexample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
    public static List x = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new HttpGetTask().execute();
    }

    private class HttpGetTask extends AsyncTask<Void, Void, List> {

        private static final String TAG = "HttpGetTask";
        private static final String URL = "http://ucd.dawngregg.com/cfunckes/json.php";

        @Override
        protected List doInBackground(Void... params) {
            List data = null;
            HttpURLConnection httpUrlConnection = null;

            try {
                httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

                InputStream in = new BufferedInputStream(
                        httpUrlConnection.getInputStream());

                JSONResponseHandler mClient = new JSONResponseHandler();
                String jdata = readStream(in);
                data = mClient.handleResponse(jdata);

            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException exception) {
                Log.e(TAG, "IOException");
            } finally {
                if (null != httpUrlConnection)
                    httpUrlConnection.disconnect();
            }
            Log.i(TAG,data.toString());
            return data;
        }

        @Override
        protected void onPostExecute(List result) {
            // Create a ListAdapter from the JSON List data
            List x = result;
            setListAdapter(new ArrayAdapter(MainActivity.this, R.layout.list_item, result));

        }

        // private utility function to get all lines from the remote URL and add them to a single string.
        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }
    }

    private class JSONResponseHandler {

        private static final String ITEM_NAME = "ITEM_NAME";
        private static final String CATALOG = "catlog";
        private static final String TAG = "JSONResponseHandler ";

        public List handleResponse(String JSONResponse) throws IOException {
            List result = new ArrayList();

            try {
                JSONObject responseObject = (JSONObject) new JSONTokener(
                        JSONResponse).nextValue();
                JSONArray catalog = responseObject.getJSONArray(CATALOG);

                for (int idx = 0; idx < catalog.length(); idx++) {

                    JSONObject earthquake = (JSONObject) catalog.get(idx);

                    result.add(earthquake.getString(ITEM_NAME));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}