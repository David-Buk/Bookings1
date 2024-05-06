package com.example.bookings;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Booking_admin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_booking);

        // Assuming 'recyclerView' is already defined in your layout
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Bookings> emailItems = new ArrayList<>();
        emailItems.add(new Bookings("sample.email@example.com", "01/01/2021", "12:00 PM"));
        // Add more items to emailItems as needed

        BookingsAdapter adapter = new BookingsAdapter(emailItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}