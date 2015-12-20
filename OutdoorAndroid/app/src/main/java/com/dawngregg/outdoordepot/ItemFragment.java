package com.dawngregg.outdoordepot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.dawngregg.outdoordepot.DatabaseOpenHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends ListFragment {


    private OnFragmentInteractionListener mListener;
    private static final String ARG_TITLE = "mytitle";
    private String mTitle;
    boolean wishList = false;


    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(String param1) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            // Added this line
            getActivity().setTitle(mTitle);
            if (mTitle.equals("Wish List")) wishList = true;
        }

        if (!wishList) {
            new HttpGetTask().execute();
            setRetainInstance(true);
        }
        else {
            List list = useWishList();
            setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list));
        }

    }

    private List useWishList() {
        List theList = new ArrayList<>();
        SQLiteDatabase read = MainActivity.mDbHelper.getReadableDatabase();
        String CMD = "SELECT * FROM " + DatabaseOpenHelper.TABLE_NAME + " WHERE " + DatabaseOpenHelper.STATUS + " =1";

        Cursor c = read.rawQuery(CMD, null);
        if (c == null){
            theList.add("No Items In List");
            return theList;
        }
        c.moveToFirst();
        while (!c.isAfterLast()){
            Log.d("list", "item is " + c.getString(2));
            theList.add(c.getString(2));
            c.moveToNext();
        }
        return theList;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction("", position);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String data, int id);
    }

    private class HttpGetTask extends AsyncTask<Void, Void, List> {

        private static final String TAG = "HttpGetTask";

        // Get your own user name at http://www.geonames.org/login
        private static final String URL = "http://dawngregg.com/ucd/xml_examples/writeJSON.php";

        @Override
        protected List doInBackground(Void... params) {
            List data = null;
            HttpURLConnection httpUrlConnection = null;

            try {

                //getting the current time in milliseconds, and creating a Date object from it:
                Date date = new Date();

                //converting it back to a milliseconds representation:
                long now = date.getTime();
                long old = 0;
                // Shared prefs
                SharedPreferences prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
                if(prefs.contains("time")) old = prefs.getLong("time", 0);
                if(now-old > 24*60*60*1000) {
                    Log.i(TAG, "Read remote data");
                    httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

                    InputStream in = new BufferedInputStream(
                            httpUrlConnection.getInputStream());

                    JSONResponseHandler mClient = new JSONResponseHandler();
                    String JSONResponse = readStream(in);
                    data = mClient.handleResponse(JSONResponse);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putLong("time", date.getTime());
                    editor.commit();
                } else {
                    Log.i(TAG, "Use stored data");
                    JSONResponseHandler mClient = new JSONResponseHandler();
                    FileRW file = new FileRW();
                    String jdata = file.readFile(getActivity());
                    data = mClient.handleResponse(jdata);
                }


            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException exception) {
                Log.e(TAG, "IOException");
            } finally {
                if (null != httpUrlConnection)
                    httpUrlConnection.disconnect();
            }
            //Log.i(TAG,data);
            return data;
        }

        @Override
        protected void onPostExecute(List result) {
            // Create a ListAdapter from the JSON List data
            setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, result));

            // Set the list choice mode to allow only one selection at a time
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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

        private final String MAIN_TAG = "CATALOG";
        private String NAME_TAG = "ITEMNAME";
        private String TAG = "JSONResponseHandler ";

        public List handleResponse(String JSONResponse) throws IOException {

            List result = new ArrayList();

            try {


                // Get top-level JSON Object - a Map
                try {
                    FileRW file = new FileRW();
                    file.writeFile(JSONResponse, getActivity());
                }catch (FileNotFoundException e){}

                // Get top-level JSON Object - a Map
                JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

                // Extract value of "earthquakes" key -- a List
                JSONArray alldata = responseObject.getJSONArray(MAIN_TAG);

                // Iterate over earthquakes list
                for (int idx = 0; idx < alldata.length(); idx++) {

                    // Get single earthquake data - a Map
                    JSONObject data1 = (JSONObject) alldata.get(idx);

                    // Summarize data as a string and add it to result
                    result.add(data1.get(NAME_TAG) );
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }



            // If an item has been selected, set its checked state?? Not sure why
            //if (-1 != mCurrIdx) getListView().setItemChecked(mCurrIdx, true);


            return result;
        }


    }

}
