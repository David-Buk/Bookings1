package com.example.bookings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bookings.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKINGS = "Bookings";
    private static final String COLUMN_ID = "booking_id";
    private static final String COLUMN_CAMPUS = "campus";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_IS_AVAILABLE = "unavailable";
    private static final String COLUMN_TIME_SLOT = "time_slot";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CAMPUS + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_IS_AVAILABLE + " INTEGER," +
                    COLUMN_TIME_SLOT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_BOOKINGS;

    public NewDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insertBooking(String campus, String date, String timeSlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAMPUS, campus);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME_SLOT, timeSlot);

        long newRowId = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return newRowId;
    }

    public long bookSlot(String campus, String date, String timeSlot) {
        if (isSlotBooked(campus, timeSlot, date)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_CAMPUS, campus);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_TIME_SLOT, timeSlot);
            values.put(COLUMN_IS_AVAILABLE, 0); // Slot is no longer available
            long result = db.insert(TABLE_BOOKINGS, null, values);
            db.close();
            return result;
        } else {
            return -1; // Indicate slot not available
        }
    }

//-------------------------------------- Test ----------------------------------------------


    public boolean isSlotBooked(String campusName, String slotTime, String bookingDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKINGS + " WHERE "
                + COLUMN_CAMPUS + " = ? AND "
                + COLUMN_TIME_SLOT + " = ? AND "
                + COLUMN_DATE + " = ?";
        String[] selectionArgs = new String[]{campusName, slotTime, bookingDate};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean isBooked = cursor.moveToFirst(); // True if cursor is not empty
        cursor.close();
        db.close();
        return isBooked;
    }
}
