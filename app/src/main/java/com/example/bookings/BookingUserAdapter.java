package com.example.bookings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BookingUserAdapter extends RecyclerView.Adapter<BookingUserAdapter.ViewHolder> {

    private List<Bookings> bookingsList;
    private List<Bookings> selectedBookings;

    public BookingUserAdapter(List<Bookings> bookingsList) {
        this.bookingsList = bookingsList;
        this.selectedBookings = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookings booking = bookingsList.get(position);
        holder.campusTextView.setText(booking.getCampus());
        holder.dateTextView.setText(booking.getDate());
        holder.timeTextView.setText(booking.getTime());

        holder.selectCheckBox.setChecked(selectedBookings.contains(booking));
        holder.selectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedBookings.add(booking);
            } else {
                selectedBookings.remove(booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public List<Bookings> getSelectedBookings() {
        return selectedBookings;
    }

    public void updateData(List<Bookings> newBookings) {
        this.bookingsList = newBookings;
        this.selectedBookings.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView campusTextView;
        public TextView dateTextView;
        public TextView timeTextView;
        public CheckBox selectCheckBox;

        public ViewHolder(View view) {
            super(view);
            campusTextView = view.findViewById(R.id.campusTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            selectCheckBox = view.findViewById(R.id.selectCheckBox);
        }
    }
}
