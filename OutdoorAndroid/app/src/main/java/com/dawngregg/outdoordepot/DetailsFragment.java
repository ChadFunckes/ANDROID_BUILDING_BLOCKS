package com.dawngregg.outdoordepot;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private OnFragmentInteractionListener mListener;
    private boolean wish = false;
    private int mID = 0;
    private String mString = "";
    private static final String TAG = "DetailsFragment";
    private final String MAIN_TAG = "CATALOG";
    private final String NAME_TAG = "ITEMNAME";
    private final String IMG_TAG = "ITEMIMAGE";
    private final String DESC_TAG = "ITEMDESC";

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mID = getArguments().getInt("myid");
            mString = getArguments().getString("mydata");
            getActivity().setTitle("Item " + mID);
            wish = getArguments().getBoolean("wish");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview =  inflater.inflate(R.layout.fragment_details, container, false);
        TextView tv = (TextView) myview.findViewById(R.id.desc);
        ImageView img = (ImageView) myview.findViewById(R.id.imageView);
        TextView tv2 = (TextView) myview.findViewById(R.id.favText);
        Button b = (Button) myview.findViewById(R.id.button2);

        try {
            FileRW file = new FileRW();
            String jdata = file.readFile(getActivity());
            JSONObject responseObject = (JSONObject) new JSONTokener(jdata).nextValue();
            JSONArray alldata = responseObject.getJSONArray(MAIN_TAG);
            final JSONObject data1 = (JSONObject) alldata.get(mID);
            getActivity().setTitle((String)data1.get(NAME_TAG));
            Log.i(TAG, "Title Set");


            //img.setImageResource(R.drawable.rsz_tree);
            new DownloadImageTask(img).execute("http://dawngregg.com/ucd/xml_examples/images/" + (String) data1.get(IMG_TAG));
            tv.setText((String) data1.get(DESC_TAG));
            if(wish) {
                tv2.setText(getString(R.string.fav));
                b.setText(getString(R.string.remove));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = null;
                        try {
                            name = (String) data1.get(NAME_TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String where = DatabaseOpenHelper.NAME + "= '" + name + "';";
                        Log.d("on click", "mString is " + NAME_TAG);
                        SQLiteDatabase db = MainActivity.mDbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(DatabaseOpenHelper.STATUS, 0);
                        db.update(DatabaseOpenHelper.TABLE_NAME, cv, where, null);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = null;
                        try {
                            name = (String) data1.get(NAME_TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SQLiteDatabase db = MainActivity.mDbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        try {
                            cv.put(DatabaseOpenHelper.NAME, (String) data1.get(NAME_TAG));
                            cv.put(DatabaseOpenHelper.DETAILS, (String) data1.get(DESC_TAG));
                            cv.put(DatabaseOpenHelper.EID, mID);
                            cv.put(DatabaseOpenHelper.STATUS, 1);
                            cv.put(DatabaseOpenHelper.RATING, 0);
                            db.insert(DatabaseOpenHelper.TABLE_NAME, null, cv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }

        } catch (MalformedURLException e) { e.printStackTrace();  }
        catch (IOException e){ e.printStackTrace();}
        catch (JSONException e){ e.printStackTrace();}

        // Inflate the layout for this fragment
        return myview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String data) {
        if (mListener != null) {
            mListener.onFragmentInteraction("");
        }
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
        public void onFragmentInteraction(String data);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            Log.i(TAG, urldisplay);

            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            Log.i(TAG, "Bitmap set");
        }
    }

}
