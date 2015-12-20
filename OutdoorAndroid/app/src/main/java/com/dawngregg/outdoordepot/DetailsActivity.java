package com.dawngregg.outdoordepot;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
/*
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
*/

public class DetailsActivity extends Activity implements DetailsFragment.OnFragmentInteractionListener {
    private static final String TAG = "OutdoorDepot";
    private FragmentManager mFragmentManager;
    private DetailsFragment dFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mFragmentManager = getFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // create home fragment
        dFrag = new DetailsFragment();
        Log.i(TAG, "DetailsActivity setFragmentData");
        Bundle args = getIntent().getExtras();
        dFrag.setArguments(args);

        // Add the FirstFragment
        fragmentTransaction.add(R.id.details_frame, dFrag, "detailsFrag");
        // Commit the FragmentTransaction
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(String data) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        return true;
    }
}
