package com.example.bookings;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import java.util.ArrayList;


public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private final List<Bookings> mData;
    private final List<Bookings> mDataFiltered;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView campusTextView;
        private final TextView dateTextView;
        private final TextView timeTextView;
        private final CheckBox selectCheckBox;

        public ViewHolder(View view) {
            super(view);
            campusTextView = view.findViewById(R.id.campusTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            selectCheckBox = view.findViewById(R.id.selectCheckBox);
        }

        public void bindData(Bookings item) {
            campusTextView.setText(item.getCampus());
            dateTextView.setText(item.getDate());
            timeTextView.setText(item.getTime());
            selectCheckBox.setChecked(false); // Assuming dynamic state based on data
        }
    }

    public BookingsAdapter(List<Bookings> data) {
        mData = data;
        mDataFiltered = new ArrayList<>(data); // copy full data
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookings, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bindData(mDataFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterByCampus(String campus) {
        mDataFiltered.clear();
        if (campus.equals("All")) {
            mDataFiltered.addAll(mData);
        } else {
            for (Bookings item : mData) {
                if (item.getCampus().equals(campus)) {
                    mDataFiltered.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
