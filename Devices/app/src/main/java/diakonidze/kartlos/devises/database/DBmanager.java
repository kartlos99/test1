package diakonidze.kartlos.devises.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import diakonidze.kartlos.devises.Devises;

/**
 * Created by kartlos on 7/8/2015.
 */
public class DBmanager {

    private static DBhelper dbhelper;
    private static SQLiteDatabase db;

    public static void initialaize(Context context){
        if(dbhelper == null){
            dbhelper = new DBhelper(context);
        }
    }

    public static void openWritable(){
        db = dbhelper.getWritableDatabase();
    }

    public static void openReadable(){
        db = dbhelper.getReadableDatabase();
    }

    public static void close(){
        db.close();
    }

//    public static void dropDeviceTable(){
//        dbhelper.dropTable(db, DBscheme.DEVICES_TABLE);
//    }

//    public static void createDeviceTable(){
//        dbhelper.createDeviceTable(db);
//    }


    public static long insertIntoDevices(Devises device){
        ContentValues values = new ContentValues();
        values.put(DBscheme.DEVICES_CATEGORY, device.getDeviceCategory());
        values.put(DBscheme.DEVICES_MODEL, device.getModel());
        values.put(DBscheme.DEVICES_BRAND, device.getBrand());
        values.put(DBscheme.DEVICES_INFO, device.getInfo());
        values.put(DBscheme.DEVICES_IMIG, device.getImig());
        values.put(DBscheme.DEVICES_COUNT, device.getCount());
        values.put(DBscheme.DEVICES_SPONSOR, device.getSponsor());
        values.put(DBscheme.DEVICES_COMENT, device.getComent());

        return db.insert(DBscheme.DEVICES_TABLE, null, values);
    }

    public static ArrayList<Devises> getDevicesList(String whare){
        ArrayList<Devises> devicesToReturn = new ArrayList<>();
        Cursor cursor = db.query(DBscheme.DEVICES_TABLE, null, whare, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DBscheme.DEVICES_ID));
                String cat = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_CATEGORY));
                String mod = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_MODEL));
                String brend = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_BRAND));
                String info = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_INFO));
                String imig = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_IMIG));
                String coment = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_COMENT));
                int sp = cursor.getInt(cursor.getColumnIndex(DBscheme.DEVICES_SPONSOR));
                int count = cursor.getInt(cursor.getColumnIndex(DBscheme.DEVICES_COUNT));


                Devises tempDevice = new Devises(cat, brend, mod, info, imig, count);
                tempDevice.setId(id);
                tempDevice.setComent(coment);
                tempDevice.setSponsor(sp);

                devicesToReturn.add(tempDevice);
            } while(cursor.moveToNext());
        }

        return devicesToReturn;
    }

    public static ArrayList<Devises> getUnicTitles(){
        ArrayList<Devises> titleList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select " + DBscheme.DEVICES_CATEGORY + ", sum(" + DBscheme.DEVICES_COUNT + ") as totalcount from " + DBscheme.DEVICES_TABLE +
                " Group by " + DBscheme.DEVICES_CATEGORY +
                " order by " + DBscheme.DEVICES_CATEGORY , null);

        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex(DBscheme.DEVICES_CATEGORY));
                int totalCount = cursor.getInt(cursor.getColumnIndex("totalcount"));

                Devises tempDevice = new Devises(title, "", "", "", "", totalCount);
                titleList.add(tempDevice);
            } while (cursor.moveToNext());
        }
        return titleList;
    }

    public static void clearTable() {
        db.delete(DBscheme.DEVICES_TABLE, null, null);
    }
}
