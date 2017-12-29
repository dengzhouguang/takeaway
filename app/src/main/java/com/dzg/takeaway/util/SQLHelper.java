package com.dzg.takeaway.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by dengzhouguang on 2017/12/3.
 */

public class SQLHelper {
    public static SQLiteDatabase database;

    public static ArrayList<String> getCityList(Context context){
        DBManager dbManager = new DBManager(context);
        dbManager.openDateBase();
        dbManager.closeDatabase();
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
                + DBManager.DB_NAME, null);
        return getCityNames();
    }
    private static ArrayList<String> getCityNames() {
        ArrayList<String> names = new ArrayList<String>();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM T_City ORDER BY NameSort", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            names.add(cursor.getString(cursor
                    .getColumnIndex("CityName")));
        }
        cursor.close();
        return names;
    }
}
