package com.dawngregg.outdoordepot;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//new
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity implements ItemFragment.OnFragmentInteractionListener {

    private static final String TAG = "OutdoorDepotActivity";
    // Add declarations to access the Fragment
    private FragmentManager mFragmentManager;
    private ItemFragment homeFrag;
    private ItemFragment wishFrag;

    //db privates
    static DatabaseOpenHelper mDbHelper;
    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity onCreate");
        setContentView(R.layout.activity_main);
        //Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // create home fragment
        homeFrag = ItemFragment.newInstance(getString(R.string.app_title));
        wishFrag = ItemFragment.newInstance(getString(R.string.win2));

        /* Code used if had a menu on DetailsActivity
        Bundle args = getIntent().getExtras();
        int mID = R.id.action_home;
        if (args!= null) mID = args.getInt("mydata");

        if(mID == R.id.action_home) {
            // Add the FirstFragment
            fragmentTransaction.add(R.id.list_frame, homeFrag, "homeFrag");
        } else{
            fragmentTransaction.add(R.id.list_frame, wishFrag, "wishFrag");
        }*/

        // Add the FirstFragment
        fragmentTransaction.add(R.id.list_frame, homeFrag, "homeFrag");

        Log.i(TAG, "MainActivity add Fragment");
        // Commit the FragmentTransaction
        fragmentTransaction.commit();
        Log.i(TAG, "MainActivity commit Fragment");

        // Create a new DatabaseHelper
        mDbHelper = new DatabaseOpenHelper(this);

        // start with an empty database
        //clearAll();

        // Insert records
        //insertCatalog();

        // Create a cursor
        //Cursor c = readCatalog();
        //mAdapter = new SimpleCursorAdapter(this, R.layout.activity_main, c, DatabaseOpenHelper.columns, new int[] { 1, 2, 3, 4, 5, 6 }, 0);

        //setListAdapter(mAdapter);

        /*Button fixButton = (Button) findViewById(R.id.fix_button);
        fixButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // execute database operations
                fix();

                // Redisplay data
                mAdapter.getCursor().requery();
                mAdapter.notifyDataSetChanged();
            }
        });*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (wishFrag.isVisible()) fragmentTransaction.remove(wishFrag).commit();
        if (homeFrag.isVisible()) fragmentTransaction.remove(homeFrag).commit();
        super.onSaveInstanceState(outState);
    }

    // Insert several artist records
        private void insertCatalog() {

            ContentValues values = new ContentValues();

            values.put(DatabaseOpenHelper.EID, "1");
            values.put(DatabaseOpenHelper.NAME, "Hiking Shorts");
            values.put(DatabaseOpenHelper.DETAILS, "Soft, comfortable, lightweight cotton twill fabric and a classic fit highlight these shorts");
            values.put(DatabaseOpenHelper.STATUS, 1);
            values.put(DatabaseOpenHelper.RATING, 5.0);
            mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);

            values.clear();

            values.put(DatabaseOpenHelper.EID, "2");
            values.put(DatabaseOpenHelper.NAME, "Fleece Pullover");
            values.put(DatabaseOpenHelper.DETAILS, "A superb springtime layering choice - the Fleece Pullover has excellent wind resistance and warmth in a streamlined package");
            values.put(DatabaseOpenHelper.STATUS, 1);
            values.put(DatabaseOpenHelper.RATING, 5.0);
            mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);

            values.clear();

            values.put(DatabaseOpenHelper.EID, "3");
            values.put(DatabaseOpenHelper.NAME, "Airstream Canvas Shoes");
            values.put(DatabaseOpenHelper.DETAILS, "These comfortable shoes tackle outdoor activities in all kinds of weather");
            values.put(DatabaseOpenHelper.STATUS, 0);
            values.put(DatabaseOpenHelper.RATING, 5.0);
            mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);

            values.clear();

            values.put(DatabaseOpenHelper.EID, "4");
            values.put(DatabaseOpenHelper.NAME, "All-Weather Mountain Parka");
            values.put(DatabaseOpenHelper.DETAILS, "This two-layer parka offers rugged, waterproof protection and excellent breathability");
            values.put(DatabaseOpenHelper.STATUS, 0);
            values.put(DatabaseOpenHelper.RATING, 5.0);
            mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
        }

        // Returns all artist records in the database
        private Cursor readCatalog() {
            return mDbHelper.getWritableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
                    DatabaseOpenHelper.columns, null, new String[] {}, null, null,
                    null);
        }

        // Modify the contents of the database
        private void fix() {

            mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME,
                    DatabaseOpenHelper.RATING + "=?",
                    new String[] { "5.0" });

            ContentValues values = new ContentValues();
            values.put(DatabaseOpenHelper.RATING, "5.0");

            mDbHelper.getWritableDatabase().update(DatabaseOpenHelper.TABLE_NAME, values,
                    DatabaseOpenHelper.RATING + "=?",
                    new String[]{"5.0"});

        }

        // Delete all records
        private void clearAll() {

            mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, null, null);

        }

        // Close database
        @Override
        protected void onDestroy() {

            mDbHelper.getWritableDatabase().close();
            //mDbHelper.deleteDatabase();

            super.onDestroy();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            if(wishFrag != null  && wishFrag.isVisible()) {
                mFragmentManager.beginTransaction().remove(wishFrag).commit();
            }
            if(homeFrag != null && !homeFrag.isVisible()) mFragmentManager.beginTransaction().add(R.id.list_frame, homeFrag).commit();
        }
        else if (id == R.id.action_wish) {
            if(homeFrag != null && homeFrag.isVisible()) {
                mFragmentManager.beginTransaction().remove(homeFrag).commit();
            }
            if(wishFrag != null  && !wishFrag.isVisible()) {
                mFragmentManager.beginTransaction().add(R.id.list_frame, wishFrag).commit();
            }
        }
        return true;
    }

    public void onFragmentInteraction(String data, int id) {
        // The user selected the item in the list from the ItemFragment
        // Do something here to display that details
        Log.i(TAG, getClass().getSimpleName() + ":onFragmentInteraction() started");

        Intent intent = new Intent(this, DetailsActivity.class);
        /** Setting data ( the clicked item's position ) to this intent */
        intent.putExtra("myid", id);
        intent.putExtra("mydata", data);
        intent.putExtra("wish", wishFrag.isVisible());
        startActivity(intent);
    }
}
