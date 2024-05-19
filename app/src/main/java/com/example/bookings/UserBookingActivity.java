package com.example.bookings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingUserAdapter bookingsAdapter;
    private DBHelper dbHelper;
    private Button deleteButton;
    private String email;  // To keep email accessible throughout the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        recyclerView = findViewById(R.id.recycleViewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new DBHelper(this);
        List<Bookings> bookings = dbHelper.getAllBookings();

        bookingsAdapter = new BookingUserAdapter(bookings);
        recyclerView.setAdapter(bookingsAdapter);

        deleteButton.setOnClickListener(v -> deleteSelectedBookings());
    }

    private void deleteSelectedBookings() {
        List<Bookings> selectedBookings = bookingsAdapter.getSelectedBookings();
        for (Bookings booking : selectedBookings) {
            dbHelper.deleteBooking(dbHelper.getBookingId(booking.getEmail(), booking.getDate(),booking.getTime()));
        }
        bookingsAdapter.updateData(dbHelper.getAllBookings());
    }
}
