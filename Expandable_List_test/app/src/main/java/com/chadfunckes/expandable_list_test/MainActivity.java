package com.chadfunckes.expandable_list_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.chadfunckes.expandable_list_test.Containers.group;
import com.chadfunckes.expandable_list_test.Containers.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;

    List<group> listGroup;
    HashMap<group, List<items>> listChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillList();

        expandableListView = (ExpandableListView) findViewById(R.id.test);

        listAdapter = new com.chadfunckes.expandable_list_test.ExpandableListAdapter(this, listGroup, listChildren);

        expandableListView.setAdapter(listAdapter);


    }

    public void fillList(){
        listGroup = new ArrayList<>();
        listChildren = new HashMap<>();

        group gone = new group();
        gone._id = 0; gone.name = "group 1"; gone.GPS = "coord"; gone.alarm = "bang";
        group gtwo = new group();
        gtwo._id = 1; gtwo.name = "group 2"; gtwo.GPS = "coord"; gtwo.alarm = "boom";
        group gthree = new group();
        gthree._id = 3; gthree.name = "group 3"; gthree.GPS = "coord"; gthree.alarm = "bip";

        listGroup.add(gone);
        listGroup.add(gtwo);
        listGroup.add(gthree);

        items testItem = new items();
        testItem._id = 0; testItem.name = "test11"; testItem.finished = false; testItem.notes = "xxx"; testItem.GPS = "coord"; testItem.alarm = "none";
        items testItem2 = new items();
        testItem2._id = 2; testItem2.name = "test12"; testItem2.finished = false; testItem2.notes = "xxx"; testItem2.GPS = "coord"; testItem2.alarm = "none";
        items testItem3 = new items();
        testItem3._id = 3; testItem3.name = "test13"; testItem3.finished = false; testItem3.notes = "xxx"; testItem3.GPS = "coord"; testItem3.alarm = "none";
        items test4 = new items();
        test4._id = 4; test4.name = "test21"; test4.finished = false; test4.notes = "xxx"; test4.GPS = "coord"; test4.alarm = "none";
        items test5 = new items();
        test5._id = 0; test5.name = "test12"; test5.finished = false; test5.notes = "xxx"; test5.GPS = "coord"; test5.alarm = "none";

        List<items> one = new ArrayList<>();
        one.add(testItem);
        one.add(testItem2);
        one.add(testItem3);
        listChildren.put(listGroup.get(0), one);

        List<items> two = new ArrayList<>();
        two.add(test4);
        two.add(test5);
        listChildren.put(listGroup.get(1), two);

        List<items> three = new ArrayList<>();
        listChildren.put(listGroup.get(2), three);

    }


}
