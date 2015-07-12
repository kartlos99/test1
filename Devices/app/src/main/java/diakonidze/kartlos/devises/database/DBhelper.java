package diakonidze.kartlos.devises.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kartlos on 7/8/2015.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GeolabDevicesDB";
    private static final int DB_VERSION = 1;

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDevicesTable());
//        createDeviceTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public void clearTable(SQLiteDatabase db, String tableName){
//        String query = "DROP TABLE IF EXISTS " + tableName;
//        db.execSQL(query);
//    }

    public void createDeviceTable(SQLiteDatabase db){
       // db.execSQL(query);
    }

    private String createDevicesTable() {
        String query = "create table if not exists " +
                DBscheme.DEVICES_TABLE + "(" +
                DBscheme.DEVICES_ID + " integer primary key autoincrement, " +
                DBscheme.DEVICES_CATEGORY + " text not null, " +
                DBscheme.DEVICES_MODEL + " text not null, " +
                DBscheme.DEVICES_BRAND + " text, " +
                DBscheme.DEVICES_INFO + " text, " +
                DBscheme.DEVICES_IMIG + " text, " +
                DBscheme.DEVICES_COUNT + " integer, " +
                DBscheme.DEVICES_SPONSOR + " integer default 0, " +
                DBscheme.DEVICES_COMENT + " text);";
        return query;
    }

}
