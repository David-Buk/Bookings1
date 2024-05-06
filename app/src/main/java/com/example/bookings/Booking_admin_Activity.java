package com.example.bookings;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

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

        // In your activity
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        Spinner campusSpinner = findViewById(R.id.campusSpinner);

        List<Bookings> bookingItems = new ArrayList<>();
        // Populate bookingItems with actual data

        BookingsAdapter adapter = new BookingsAdapter(bookingItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the spinner
        campusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCampus = parent.getItemAtPosition(position).toString();
                adapter.filterByCampus(selectedCampus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}