package com.example.bookings;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bookings.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }



    // ------------------ User Table -------------------------------------------------------------------------------

    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_EMAIL + " TEXT NOT NULL," +
                    COLUMN_PASSWORD + " TEXT NOT NULL," +
                    COLUMN_ROLE + " TEXT NOT NULL)";


    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;


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



    // ------------------ Bookings Table -------------------------------------------------------------------------------

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKINGS = "Bookings";
    private static final String COLUMN_ID = "booking_id";
    private static final String COLUMN_CAMPUS = "campus";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME_SLOT = "time_slot";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CAMPUS + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TIME_SLOT + " TEXT)";
                    //+ "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_BOOKINGS;



    public long insertBooking(/**int userId,**/ String campus, String date, String timeSlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_CAMPUS, campus);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME_SLOT, timeSlot);

        long newRowId = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return newRowId;
    }


    public List<Bookings> getAllBookings() {
        List<Bookings> bookingsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Bookings", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Bookings booking = new Bookings(
                        //cursor.getString(cursor.getColumnIndex("booking_id")),
                        //cursor.getString(cursor.getColumnIndex("user_id")),
                        cursor.getString(cursor.getColumnIndex("campus")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("time_slot"))
                );
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingsList;
    }


    public List<Bookings> getBookingsByUserId(String userId) {
        List<Bookings> bookingsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_USER_ID, COLUMN_CAMPUS, COLUMN_DATE, COLUMN_TIME_SLOT};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Bookings booking = new Bookings(
                        //cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        //cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CAMPUS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TIME_SLOT))
                );
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingsList;
    }

}
