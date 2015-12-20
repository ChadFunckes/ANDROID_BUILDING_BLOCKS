package com.dawngregg.outdoordepot;

/**
 * Created by Debbie on 11/27/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "catalog";
    final static String _ID = "_id";
    final static String EID = "eid";
    final static String NAME = "name";
    final static String DETAILS = "details";
    final static String STATUS = "status";
    final static String RATING = "rating";

    final static String[] columns = {  _ID, EID, NAME, DETAILS, STATUS, RATING };

    final private static String CREATE_CMD = "CREATE TABLE catalog (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EID + " TEXT NOT NULL,"
                    + NAME + " TEXT NOT NULL,"
                    + DETAILS + " TEXT NOT NULL,"
                    + STATUS + " INTEGER NOT NULL,"
                    + RATING + " REAL NOT NULL"
                    + ")";

    final private static String dbNAME = "outdoorDepot_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DatabaseOpenHelper(Context context) {
        super(context, dbNAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    void deleteDatabase() {
        mContext.deleteDatabase(dbNAME);
    }

//    public List onSelect(String queryString){
//
//        List result = new ArrayList();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM catalog;";
//
//        //SQLiteDatabase db = this.getWritableDatabase();
//        //Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        //if (cursor.moveToFirst()) {
//            //do {
////                    Contact contact = new Contact();
////                    contact.setID(Integer.parseInt(cursor.getString(0)));
////                    contact.setName(cursor.getString(1));
////                    contact.setPhoneNumber(cursor.getString(2));
////                    // Adding contact to list
////                    contactList.add(contact);
//                //result.add(cursor.getString(0));
//           //} while (cursor.moveToNext());
//        //}
//
//        // return contact list
//        return result;
//    }
}

//    final private static String NAME = "outdoorDepot_db";
//    final private static Integer VERSION = 1;
//    final private Context mContext;
//
//
//    // Database Name
//    //final static String DATABASE_NAME = "outdoorDepot_db";
//
//    // Catalog table name
//    //final String TABLE_CONTACTS = "catalog";
//
////    // Contacts Table Columns names
////    private static final String EID = "id";
////    private static final String name = "name";
////    private static final String KEY_PH_NO = "phone_number";
////    private static final Integer KEY_ID = "id";
////    private static final Float KEY_ID = "id";
//
//    //String eid,String name,String details,Int status,Float rating
//
//    //public DatabaseOpenHelper(Context context){
//    public DatabaseOpenHelper(Context context){
//        super(context, NAME, null, VERSION);
//        this.mContext = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        //db.execSQL(CREATE_CMD);
//        //not used
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        //not used
//    }
//
//    public List onSelect(String queryString){
//
//        List result = new ArrayList();
//            // Select All Query
//            String selectQuery = "SELECT  * FROM catalog";
//
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
////                    Contact contact = new Contact();
////                    contact.setID(Integer.parseInt(cursor.getString(0)));
////                    contact.setName(cursor.getString(1));
////                    contact.setPhoneNumber(cursor.getString(2));
////                    // Adding contact to list
////                    contactList.add(contact);
//                    result.add(cursor.getString(0));
//                } while (cursor.moveToNext());
//            }
//
//            // return contact list
//            return result;
//        }
//
//    void deleteDatabase() {
//        //yeah...don't call this
//        // //mContext.deleteDatabase(NAME);
//    }
//
//}
